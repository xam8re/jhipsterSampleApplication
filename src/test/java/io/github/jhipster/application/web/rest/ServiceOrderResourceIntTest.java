package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.ServiceOrder;
import io.github.jhipster.application.repository.ServiceOrderRepository;
import io.github.jhipster.application.repository.search.ServiceOrderSearchRepository;
import io.github.jhipster.application.service.ServiceOrderService;
import io.github.jhipster.application.service.dto.ServiceOrderDTO;
import io.github.jhipster.application.service.mapper.ServiceOrderMapper;
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
import java.util.Collections;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.application.domain.enumeration.OrderState;
/**
 * Test class for the ServiceOrderResource REST controller.
 *
 * @see ServiceOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ServiceOrderResourceIntTest {

    private static final OrderState DEFAULT_STATE = OrderState.PENDING;
    private static final OrderState UPDATED_STATE = OrderState.WIP;

    @Autowired
    private ServiceOrderRepository serviceOrderRepository;


    @Autowired
    private ServiceOrderMapper serviceOrderMapper;
    

    @Autowired
    private ServiceOrderService serviceOrderService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.ServiceOrderSearchRepositoryMockConfiguration
     */
    @Autowired
    private ServiceOrderSearchRepository mockServiceOrderSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServiceOrderMockMvc;

    private ServiceOrder serviceOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceOrderResource serviceOrderResource = new ServiceOrderResource(serviceOrderService);
        this.restServiceOrderMockMvc = MockMvcBuilders.standaloneSetup(serviceOrderResource)
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
    public static ServiceOrder createEntity(EntityManager em) {
        ServiceOrder serviceOrder = new ServiceOrder()
            .state(DEFAULT_STATE);
        return serviceOrder;
    }

    @Before
    public void initTest() {
        serviceOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceOrder() throws Exception {
        int databaseSizeBeforeCreate = serviceOrderRepository.findAll().size();

        // Create the ServiceOrder
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);
        restServiceOrderMockMvc.perform(post("/api/service-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceOrder in the database
        List<ServiceOrder> serviceOrderList = serviceOrderRepository.findAll();
        assertThat(serviceOrderList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceOrder testServiceOrder = serviceOrderList.get(serviceOrderList.size() - 1);
        assertThat(testServiceOrder.getState()).isEqualTo(DEFAULT_STATE);

        // Validate the ServiceOrder in Elasticsearch
        verify(mockServiceOrderSearchRepository, times(1)).save(testServiceOrder);
    }

    @Test
    @Transactional
    public void createServiceOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceOrderRepository.findAll().size();

        // Create the ServiceOrder with an existing ID
        serviceOrder.setId(1L);
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOrderMockMvc.perform(post("/api/service-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrder in the database
        List<ServiceOrder> serviceOrderList = serviceOrderRepository.findAll();
        assertThat(serviceOrderList).hasSize(databaseSizeBeforeCreate);

        // Validate the ServiceOrder in Elasticsearch
        verify(mockServiceOrderSearchRepository, times(0)).save(serviceOrder);
    }

    @Test
    @Transactional
    public void getAllServiceOrders() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList
        restServiceOrderMockMvc.perform(get("/api/service-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }
    

    @Test
    @Transactional
    public void getServiceOrder() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get the serviceOrder
        restServiceOrderMockMvc.perform(get("/api/service-orders/{id}", serviceOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceOrder.getId().intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingServiceOrder() throws Exception {
        // Get the serviceOrder
        restServiceOrderMockMvc.perform(get("/api/service-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceOrder() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        int databaseSizeBeforeUpdate = serviceOrderRepository.findAll().size();

        // Update the serviceOrder
        ServiceOrder updatedServiceOrder = serviceOrderRepository.findById(serviceOrder.getId()).get();
        // Disconnect from session so that the updates on updatedServiceOrder are not directly saved in db
        em.detach(updatedServiceOrder);
        updatedServiceOrder
            .state(UPDATED_STATE);
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(updatedServiceOrder);

        restServiceOrderMockMvc.perform(put("/api/service-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOrderDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceOrder in the database
        List<ServiceOrder> serviceOrderList = serviceOrderRepository.findAll();
        assertThat(serviceOrderList).hasSize(databaseSizeBeforeUpdate);
        ServiceOrder testServiceOrder = serviceOrderList.get(serviceOrderList.size() - 1);
        assertThat(testServiceOrder.getState()).isEqualTo(UPDATED_STATE);

        // Validate the ServiceOrder in Elasticsearch
        verify(mockServiceOrderSearchRepository, times(1)).save(testServiceOrder);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceOrder() throws Exception {
        int databaseSizeBeforeUpdate = serviceOrderRepository.findAll().size();

        // Create the ServiceOrder
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restServiceOrderMockMvc.perform(put("/api/service-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrder in the database
        List<ServiceOrder> serviceOrderList = serviceOrderRepository.findAll();
        assertThat(serviceOrderList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ServiceOrder in Elasticsearch
        verify(mockServiceOrderSearchRepository, times(0)).save(serviceOrder);
    }

    @Test
    @Transactional
    public void deleteServiceOrder() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);

        int databaseSizeBeforeDelete = serviceOrderRepository.findAll().size();

        // Get the serviceOrder
        restServiceOrderMockMvc.perform(delete("/api/service-orders/{id}", serviceOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ServiceOrder> serviceOrderList = serviceOrderRepository.findAll();
        assertThat(serviceOrderList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ServiceOrder in Elasticsearch
        verify(mockServiceOrderSearchRepository, times(1)).deleteById(serviceOrder.getId());
    }

    @Test
    @Transactional
    public void searchServiceOrder() throws Exception {
        // Initialize the database
        serviceOrderRepository.saveAndFlush(serviceOrder);
        when(mockServiceOrderSearchRepository.search(queryStringQuery("id:" + serviceOrder.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(serviceOrder), PageRequest.of(0, 1), 1));
        // Search the serviceOrder
        restServiceOrderMockMvc.perform(get("/api/_search/service-orders?query=id:" + serviceOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOrder.class);
        ServiceOrder serviceOrder1 = new ServiceOrder();
        serviceOrder1.setId(1L);
        ServiceOrder serviceOrder2 = new ServiceOrder();
        serviceOrder2.setId(serviceOrder1.getId());
        assertThat(serviceOrder1).isEqualTo(serviceOrder2);
        serviceOrder2.setId(2L);
        assertThat(serviceOrder1).isNotEqualTo(serviceOrder2);
        serviceOrder1.setId(null);
        assertThat(serviceOrder1).isNotEqualTo(serviceOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOrderDTO.class);
        ServiceOrderDTO serviceOrderDTO1 = new ServiceOrderDTO();
        serviceOrderDTO1.setId(1L);
        ServiceOrderDTO serviceOrderDTO2 = new ServiceOrderDTO();
        assertThat(serviceOrderDTO1).isNotEqualTo(serviceOrderDTO2);
        serviceOrderDTO2.setId(serviceOrderDTO1.getId());
        assertThat(serviceOrderDTO1).isEqualTo(serviceOrderDTO2);
        serviceOrderDTO2.setId(2L);
        assertThat(serviceOrderDTO1).isNotEqualTo(serviceOrderDTO2);
        serviceOrderDTO1.setId(null);
        assertThat(serviceOrderDTO1).isNotEqualTo(serviceOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceOrderMapper.fromId(null)).isNull();
    }
}
