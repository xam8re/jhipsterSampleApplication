package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.TaskOffer;
import io.github.jhipster.application.domain.AMSAUser;
import io.github.jhipster.application.repository.TaskOfferRepository;
import io.github.jhipster.application.repository.search.TaskOfferSearchRepository;
import io.github.jhipster.application.service.TaskOfferService;
import io.github.jhipster.application.service.dto.TaskOfferDTO;
import io.github.jhipster.application.service.mapper.TaskOfferMapper;
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
 * Test class for the TaskOfferResource REST controller.
 *
 * @see TaskOfferResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class TaskOfferResourceIntTest {

    private static final Double DEFAULT_PRIZE = 1D;
    private static final Double UPDATED_PRIZE = 2D;

    private static final OfferState DEFAULT_STATE = OfferState.PENDING;
    private static final OfferState UPDATED_STATE = OfferState.COMPLETED;

    private static final Instant DEFAULT_PERIOD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PERIOD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TaskOfferRepository taskOfferRepository;


    @Autowired
    private TaskOfferMapper taskOfferMapper;
    

    @Autowired
    private TaskOfferService taskOfferService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.TaskOfferSearchRepositoryMockConfiguration
     */
    @Autowired
    private TaskOfferSearchRepository mockTaskOfferSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTaskOfferMockMvc;

    private TaskOffer taskOffer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaskOfferResource taskOfferResource = new TaskOfferResource(taskOfferService);
        this.restTaskOfferMockMvc = MockMvcBuilders.standaloneSetup(taskOfferResource)
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
    public static TaskOffer createEntity(EntityManager em) {
        TaskOffer taskOffer = new TaskOffer()
            .prize(DEFAULT_PRIZE)
            .state(DEFAULT_STATE)
            .period(DEFAULT_PERIOD);
        // Add required entity
        AMSAUser aMSAUser = AMSAUserResourceIntTest.createEntity(em);
        em.persist(aMSAUser);
        em.flush();
        taskOffer.setSupplier(aMSAUser);
        return taskOffer;
    }

    @Before
    public void initTest() {
        taskOffer = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskOffer() throws Exception {
        int databaseSizeBeforeCreate = taskOfferRepository.findAll().size();

        // Create the TaskOffer
        TaskOfferDTO taskOfferDTO = taskOfferMapper.toDto(taskOffer);
        restTaskOfferMockMvc.perform(post("/api/task-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskOfferDTO)))
            .andExpect(status().isCreated());

        // Validate the TaskOffer in the database
        List<TaskOffer> taskOfferList = taskOfferRepository.findAll();
        assertThat(taskOfferList).hasSize(databaseSizeBeforeCreate + 1);
        TaskOffer testTaskOffer = taskOfferList.get(taskOfferList.size() - 1);
        assertThat(testTaskOffer.getPrize()).isEqualTo(DEFAULT_PRIZE);
        assertThat(testTaskOffer.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testTaskOffer.getPeriod()).isEqualTo(DEFAULT_PERIOD);

        // Validate the TaskOffer in Elasticsearch
        verify(mockTaskOfferSearchRepository, times(1)).save(testTaskOffer);
    }

    @Test
    @Transactional
    public void createTaskOfferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskOfferRepository.findAll().size();

        // Create the TaskOffer with an existing ID
        taskOffer.setId(1L);
        TaskOfferDTO taskOfferDTO = taskOfferMapper.toDto(taskOffer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskOfferMockMvc.perform(post("/api/task-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskOfferDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskOffer in the database
        List<TaskOffer> taskOfferList = taskOfferRepository.findAll();
        assertThat(taskOfferList).hasSize(databaseSizeBeforeCreate);

        // Validate the TaskOffer in Elasticsearch
        verify(mockTaskOfferSearchRepository, times(0)).save(taskOffer);
    }

    @Test
    @Transactional
    public void getAllTaskOffers() throws Exception {
        // Initialize the database
        taskOfferRepository.saveAndFlush(taskOffer);

        // Get all the taskOfferList
        restTaskOfferMockMvc.perform(get("/api/task-offers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskOffer.getId().intValue())))
            .andExpect(jsonPath("$.[*].prize").value(hasItem(DEFAULT_PRIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD.toString())));
    }
    

    @Test
    @Transactional
    public void getTaskOffer() throws Exception {
        // Initialize the database
        taskOfferRepository.saveAndFlush(taskOffer);

        // Get the taskOffer
        restTaskOfferMockMvc.perform(get("/api/task-offers/{id}", taskOffer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taskOffer.getId().intValue()))
            .andExpect(jsonPath("$.prize").value(DEFAULT_PRIZE.doubleValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.period").value(DEFAULT_PERIOD.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTaskOffer() throws Exception {
        // Get the taskOffer
        restTaskOfferMockMvc.perform(get("/api/task-offers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskOffer() throws Exception {
        // Initialize the database
        taskOfferRepository.saveAndFlush(taskOffer);

        int databaseSizeBeforeUpdate = taskOfferRepository.findAll().size();

        // Update the taskOffer
        TaskOffer updatedTaskOffer = taskOfferRepository.findById(taskOffer.getId()).get();
        // Disconnect from session so that the updates on updatedTaskOffer are not directly saved in db
        em.detach(updatedTaskOffer);
        updatedTaskOffer
            .prize(UPDATED_PRIZE)
            .state(UPDATED_STATE)
            .period(UPDATED_PERIOD);
        TaskOfferDTO taskOfferDTO = taskOfferMapper.toDto(updatedTaskOffer);

        restTaskOfferMockMvc.perform(put("/api/task-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskOfferDTO)))
            .andExpect(status().isOk());

        // Validate the TaskOffer in the database
        List<TaskOffer> taskOfferList = taskOfferRepository.findAll();
        assertThat(taskOfferList).hasSize(databaseSizeBeforeUpdate);
        TaskOffer testTaskOffer = taskOfferList.get(taskOfferList.size() - 1);
        assertThat(testTaskOffer.getPrize()).isEqualTo(UPDATED_PRIZE);
        assertThat(testTaskOffer.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testTaskOffer.getPeriod()).isEqualTo(UPDATED_PERIOD);

        // Validate the TaskOffer in Elasticsearch
        verify(mockTaskOfferSearchRepository, times(1)).save(testTaskOffer);
    }

    @Test
    @Transactional
    public void updateNonExistingTaskOffer() throws Exception {
        int databaseSizeBeforeUpdate = taskOfferRepository.findAll().size();

        // Create the TaskOffer
        TaskOfferDTO taskOfferDTO = taskOfferMapper.toDto(taskOffer);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTaskOfferMockMvc.perform(put("/api/task-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskOfferDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskOffer in the database
        List<TaskOffer> taskOfferList = taskOfferRepository.findAll();
        assertThat(taskOfferList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TaskOffer in Elasticsearch
        verify(mockTaskOfferSearchRepository, times(0)).save(taskOffer);
    }

    @Test
    @Transactional
    public void deleteTaskOffer() throws Exception {
        // Initialize the database
        taskOfferRepository.saveAndFlush(taskOffer);

        int databaseSizeBeforeDelete = taskOfferRepository.findAll().size();

        // Get the taskOffer
        restTaskOfferMockMvc.perform(delete("/api/task-offers/{id}", taskOffer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TaskOffer> taskOfferList = taskOfferRepository.findAll();
        assertThat(taskOfferList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TaskOffer in Elasticsearch
        verify(mockTaskOfferSearchRepository, times(1)).deleteById(taskOffer.getId());
    }

    @Test
    @Transactional
    public void searchTaskOffer() throws Exception {
        // Initialize the database
        taskOfferRepository.saveAndFlush(taskOffer);
        when(mockTaskOfferSearchRepository.search(queryStringQuery("id:" + taskOffer.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(taskOffer), PageRequest.of(0, 1), 1));
        // Search the taskOffer
        restTaskOfferMockMvc.perform(get("/api/_search/task-offers?query=id:" + taskOffer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskOffer.getId().intValue())))
            .andExpect(jsonPath("$.[*].prize").value(hasItem(DEFAULT_PRIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskOffer.class);
        TaskOffer taskOffer1 = new TaskOffer();
        taskOffer1.setId(1L);
        TaskOffer taskOffer2 = new TaskOffer();
        taskOffer2.setId(taskOffer1.getId());
        assertThat(taskOffer1).isEqualTo(taskOffer2);
        taskOffer2.setId(2L);
        assertThat(taskOffer1).isNotEqualTo(taskOffer2);
        taskOffer1.setId(null);
        assertThat(taskOffer1).isNotEqualTo(taskOffer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskOfferDTO.class);
        TaskOfferDTO taskOfferDTO1 = new TaskOfferDTO();
        taskOfferDTO1.setId(1L);
        TaskOfferDTO taskOfferDTO2 = new TaskOfferDTO();
        assertThat(taskOfferDTO1).isNotEqualTo(taskOfferDTO2);
        taskOfferDTO2.setId(taskOfferDTO1.getId());
        assertThat(taskOfferDTO1).isEqualTo(taskOfferDTO2);
        taskOfferDTO2.setId(2L);
        assertThat(taskOfferDTO1).isNotEqualTo(taskOfferDTO2);
        taskOfferDTO1.setId(null);
        assertThat(taskOfferDTO1).isNotEqualTo(taskOfferDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(taskOfferMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(taskOfferMapper.fromId(null)).isNull();
    }
}
