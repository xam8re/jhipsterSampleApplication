package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.ServiceOffer;
import io.github.jhipster.application.domain.AMSAUser;
import io.github.jhipster.application.repository.ServiceOfferRepository;
import io.github.jhipster.application.repository.search.ServiceOfferSearchRepository;
import io.github.jhipster.application.service.ServiceOfferService;
import io.github.jhipster.application.service.dto.ServiceOfferDTO;
import io.github.jhipster.application.service.mapper.ServiceOfferMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.application.domain.enumeration.OfferState;
/**
 * Test class for the ServiceOfferResource REST controller.
 *
 * @see ServiceOfferResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ServiceOfferResourceIntTest {

    private static final Double DEFAULT_PRIZE = 1D;
    private static final Double UPDATED_PRIZE = 2D;

    private static final OfferState DEFAULT_STATE = OfferState.PENDING;
    private static final OfferState UPDATED_STATE = OfferState.COMPLETED;

    private static final Instant DEFAULT_PERIOD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PERIOD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ServiceOfferRepository serviceOfferRepository;


    @Autowired
    private ServiceOfferMapper serviceOfferMapper;
    

    @Autowired
    private ServiceOfferService serviceOfferService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.ServiceOfferSearchRepositoryMockConfiguration
     */
    @Autowired
    private ServiceOfferSearchRepository mockServiceOfferSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServiceOfferMockMvc;

    private ServiceOffer serviceOffer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceOfferResource serviceOfferResource = new ServiceOfferResource(serviceOfferService);
        this.restServiceOfferMockMvc = MockMvcBuilders.standaloneSetup(serviceOfferResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceOffer createEntity(EntityManager em) {
        ServiceOffer serviceOffer = new ServiceOffer()
            .prize(DEFAULT_PRIZE)
            .state(DEFAULT_STATE)
            .period(DEFAULT_PERIOD);
        // Add required entity
        AMSAUser aMSAUser = AMSAUserResourceIntTest.createEntity(em);
        em.persist(aMSAUser);
        em.flush();
        serviceOffer.setAmsaUser(aMSAUser);
        return serviceOffer;
    }

    @Before
    public void initTest() {
        serviceOffer = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceOffer() throws Exception {
        int databaseSizeBeforeCreate = serviceOfferRepository.findAll().size();

        // Create the ServiceOffer
        ServiceOfferDTO serviceOfferDTO = serviceOfferMapper.toDto(serviceOffer);
        restServiceOfferMockMvc.perform(post("/api/service-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOfferDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceOffer in the database
        List<ServiceOffer> serviceOfferList = serviceOfferRepository.findAll();
        assertThat(serviceOfferList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceOffer testServiceOffer = serviceOfferList.get(serviceOfferList.size() - 1);
        assertThat(testServiceOffer.getPrize()).isEqualTo(DEFAULT_PRIZE);
        assertThat(testServiceOffer.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testServiceOffer.getPeriod()).isEqualTo(DEFAULT_PERIOD);

        // Validate the ServiceOffer in Elasticsearch
        verify(mockServiceOfferSearchRepository, times(1)).save(testServiceOffer);
    }

    @Test
    @Transactional
    public void createServiceOfferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceOfferRepository.findAll().size();

        // Create the ServiceOffer with an existing ID
        serviceOffer.setId(1L);
        ServiceOfferDTO serviceOfferDTO = serviceOfferMapper.toDto(serviceOffer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOfferMockMvc.perform(post("/api/service-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOfferDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOffer in the database
        List<ServiceOffer> serviceOfferList = serviceOfferRepository.findAll();
        assertThat(serviceOfferList).hasSize(databaseSizeBeforeCreate);

        // Validate the ServiceOffer in Elasticsearch
        verify(mockServiceOfferSearchRepository, times(0)).save(serviceOffer);
    }

    @Test
    @Transactional
    public void getAllServiceOffers() throws Exception {
        // Initialize the database
        serviceOfferRepository.saveAndFlush(serviceOffer);

        // Get all the serviceOfferList
        restServiceOfferMockMvc.perform(get("/api/service-offers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOffer.getId().intValue())))
            .andExpect(jsonPath("$.[*].prize").value(hasItem(DEFAULT_PRIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD.toString())));
    }
    

    @Test
    @Transactional
    public void getServiceOffer() throws Exception {
        // Initialize the database
        serviceOfferRepository.saveAndFlush(serviceOffer);

        // Get the serviceOffer
        restServiceOfferMockMvc.perform(get("/api/service-offers/{id}", serviceOffer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceOffer.getId().intValue()))
            .andExpect(jsonPath("$.prize").value(DEFAULT_PRIZE.doubleValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.period").value(DEFAULT_PERIOD.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingServiceOffer() throws Exception {
        // Get the serviceOffer
        restServiceOfferMockMvc.perform(get("/api/service-offers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceOffer() throws Exception {
        // Initialize the database
        serviceOfferRepository.saveAndFlush(serviceOffer);

        int databaseSizeBeforeUpdate = serviceOfferRepository.findAll().size();

        // Update the serviceOffer
        ServiceOffer updatedServiceOffer = serviceOfferRepository.findById(serviceOffer.getId()).get();
        // Disconnect from session so that the updates on updatedServiceOffer are not directly saved in db
        em.detach(updatedServiceOffer);
        updatedServiceOffer
            .prize(UPDATED_PRIZE)
            .state(UPDATED_STATE)
            .period(UPDATED_PERIOD);
        ServiceOfferDTO serviceOfferDTO = serviceOfferMapper.toDto(updatedServiceOffer);

        restServiceOfferMockMvc.perform(put("/api/service-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOfferDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceOffer in the database
        List<ServiceOffer> serviceOfferList = serviceOfferRepository.findAll();
        assertThat(serviceOfferList).hasSize(databaseSizeBeforeUpdate);
        ServiceOffer testServiceOffer = serviceOfferList.get(serviceOfferList.size() - 1);
        assertThat(testServiceOffer.getPrize()).isEqualTo(UPDATED_PRIZE);
        assertThat(testServiceOffer.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testServiceOffer.getPeriod()).isEqualTo(UPDATED_PERIOD);

        // Validate the ServiceOffer in Elasticsearch
        verify(mockServiceOfferSearchRepository, times(1)).save(testServiceOffer);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceOffer() throws Exception {
        int databaseSizeBeforeUpdate = serviceOfferRepository.findAll().size();

        // Create the ServiceOffer
        ServiceOfferDTO serviceOfferDTO = serviceOfferMapper.toDto(serviceOffer);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restServiceOfferMockMvc.perform(put("/api/service-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOfferDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOffer in the database
        List<ServiceOffer> serviceOfferList = serviceOfferRepository.findAll();
        assertThat(serviceOfferList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ServiceOffer in Elasticsearch
        verify(mockServiceOfferSearchRepository, times(0)).save(serviceOffer);
    }

    @Test
    @Transactional
    public void deleteServiceOffer() throws Exception {
        // Initialize the database
        serviceOfferRepository.saveAndFlush(serviceOffer);

        int databaseSizeBeforeDelete = serviceOfferRepository.findAll().size();

        // Get the serviceOffer
        restServiceOfferMockMvc.perform(delete("/api/service-offers/{id}", serviceOffer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ServiceOffer> serviceOfferList = serviceOfferRepository.findAll();
        assertThat(serviceOfferList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ServiceOffer in Elasticsearch
        verify(mockServiceOfferSearchRepository, times(1)).deleteById(serviceOffer.getId());
    }

    @Test
    @Transactional
    public void searchServiceOffer() throws Exception {
        // Initialize the database
        serviceOfferRepository.saveAndFlush(serviceOffer);
        when(mockServiceOfferSearchRepository.search(queryStringQuery("id:" + serviceOffer.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(serviceOffer), PageRequest.of(0, 1), 1));
        // Search the serviceOffer
        restServiceOfferMockMvc.perform(get("/api/_search/service-offers?query=id:" + serviceOffer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOffer.getId().intValue())))
            .andExpect(jsonPath("$.[*].prize").value(hasItem(DEFAULT_PRIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOffer.class);
        ServiceOffer serviceOffer1 = new ServiceOffer();
        serviceOffer1.setId(1L);
        ServiceOffer serviceOffer2 = new ServiceOffer();
        serviceOffer2.setId(serviceOffer1.getId());
        assertThat(serviceOffer1).isEqualTo(serviceOffer2);
        serviceOffer2.setId(2L);
        assertThat(serviceOffer1).isNotEqualTo(serviceOffer2);
        serviceOffer1.setId(null);
        assertThat(serviceOffer1).isNotEqualTo(serviceOffer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOfferDTO.class);
        ServiceOfferDTO serviceOfferDTO1 = new ServiceOfferDTO();
        serviceOfferDTO1.setId(1L);
        ServiceOfferDTO serviceOfferDTO2 = new ServiceOfferDTO();
        assertThat(serviceOfferDTO1).isNotEqualTo(serviceOfferDTO2);
        serviceOfferDTO2.setId(serviceOfferDTO1.getId());
        assertThat(serviceOfferDTO1).isEqualTo(serviceOfferDTO2);
        serviceOfferDTO2.setId(2L);
        assertThat(serviceOfferDTO1).isNotEqualTo(serviceOfferDTO2);
        serviceOfferDTO1.setId(null);
        assertThat(serviceOfferDTO1).isNotEqualTo(serviceOfferDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceOfferMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceOfferMapper.fromId(null)).isNull();
    }
}
