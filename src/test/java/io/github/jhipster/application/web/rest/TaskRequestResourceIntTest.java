package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.TaskRequest;
import io.github.jhipster.application.repository.TaskRequestRepository;
import io.github.jhipster.application.repository.search.TaskRequestSearchRepository;
import io.github.jhipster.application.service.TaskRequestService;
import io.github.jhipster.application.service.dto.TaskRequestDTO;
import io.github.jhipster.application.service.mapper.TaskRequestMapper;
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

/**
 * Test class for the TaskRequestResource REST controller.
 *
 * @see TaskRequestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class TaskRequestResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_PERIOD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PERIOD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TaskRequestRepository taskRequestRepository;


    @Autowired
    private TaskRequestMapper taskRequestMapper;
    

    @Autowired
    private TaskRequestService taskRequestService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.TaskRequestSearchRepositoryMockConfiguration
     */
    @Autowired
    private TaskRequestSearchRepository mockTaskRequestSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTaskRequestMockMvc;

    private TaskRequest taskRequest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaskRequestResource taskRequestResource = new TaskRequestResource(taskRequestService);
        this.restTaskRequestMockMvc = MockMvcBuilders.standaloneSetup(taskRequestResource)
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
    public static TaskRequest createEntity(EntityManager em) {
        TaskRequest taskRequest = new TaskRequest()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .period(DEFAULT_PERIOD);
        return taskRequest;
    }

    @Before
    public void initTest() {
        taskRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskRequest() throws Exception {
        int databaseSizeBeforeCreate = taskRequestRepository.findAll().size();

        // Create the TaskRequest
        TaskRequestDTO taskRequestDTO = taskRequestMapper.toDto(taskRequest);
        restTaskRequestMockMvc.perform(post("/api/task-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskRequestDTO)))
            .andExpect(status().isCreated());

        // Validate the TaskRequest in the database
        List<TaskRequest> taskRequestList = taskRequestRepository.findAll();
        assertThat(taskRequestList).hasSize(databaseSizeBeforeCreate + 1);
        TaskRequest testTaskRequest = taskRequestList.get(taskRequestList.size() - 1);
        assertThat(testTaskRequest.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTaskRequest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaskRequest.getPeriod()).isEqualTo(DEFAULT_PERIOD);

        // Validate the TaskRequest in Elasticsearch
        verify(mockTaskRequestSearchRepository, times(1)).save(testTaskRequest);
    }

    @Test
    @Transactional
    public void createTaskRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskRequestRepository.findAll().size();

        // Create the TaskRequest with an existing ID
        taskRequest.setId(1L);
        TaskRequestDTO taskRequestDTO = taskRequestMapper.toDto(taskRequest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskRequestMockMvc.perform(post("/api/task-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskRequest in the database
        List<TaskRequest> taskRequestList = taskRequestRepository.findAll();
        assertThat(taskRequestList).hasSize(databaseSizeBeforeCreate);

        // Validate the TaskRequest in Elasticsearch
        verify(mockTaskRequestSearchRepository, times(0)).save(taskRequest);
    }

    @Test
    @Transactional
    public void getAllTaskRequests() throws Exception {
        // Initialize the database
        taskRequestRepository.saveAndFlush(taskRequest);

        // Get all the taskRequestList
        restTaskRequestMockMvc.perform(get("/api/task-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD.toString())));
    }
    

    @Test
    @Transactional
    public void getTaskRequest() throws Exception {
        // Initialize the database
        taskRequestRepository.saveAndFlush(taskRequest);

        // Get the taskRequest
        restTaskRequestMockMvc.perform(get("/api/task-requests/{id}", taskRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taskRequest.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.period").value(DEFAULT_PERIOD.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTaskRequest() throws Exception {
        // Get the taskRequest
        restTaskRequestMockMvc.perform(get("/api/task-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskRequest() throws Exception {
        // Initialize the database
        taskRequestRepository.saveAndFlush(taskRequest);

        int databaseSizeBeforeUpdate = taskRequestRepository.findAll().size();

        // Update the taskRequest
        TaskRequest updatedTaskRequest = taskRequestRepository.findById(taskRequest.getId()).get();
        // Disconnect from session so that the updates on updatedTaskRequest are not directly saved in db
        em.detach(updatedTaskRequest);
        updatedTaskRequest
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .period(UPDATED_PERIOD);
        TaskRequestDTO taskRequestDTO = taskRequestMapper.toDto(updatedTaskRequest);

        restTaskRequestMockMvc.perform(put("/api/task-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskRequestDTO)))
            .andExpect(status().isOk());

        // Validate the TaskRequest in the database
        List<TaskRequest> taskRequestList = taskRequestRepository.findAll();
        assertThat(taskRequestList).hasSize(databaseSizeBeforeUpdate);
        TaskRequest testTaskRequest = taskRequestList.get(taskRequestList.size() - 1);
        assertThat(testTaskRequest.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTaskRequest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaskRequest.getPeriod()).isEqualTo(UPDATED_PERIOD);

        // Validate the TaskRequest in Elasticsearch
        verify(mockTaskRequestSearchRepository, times(1)).save(testTaskRequest);
    }

    @Test
    @Transactional
    public void updateNonExistingTaskRequest() throws Exception {
        int databaseSizeBeforeUpdate = taskRequestRepository.findAll().size();

        // Create the TaskRequest
        TaskRequestDTO taskRequestDTO = taskRequestMapper.toDto(taskRequest);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTaskRequestMockMvc.perform(put("/api/task-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskRequest in the database
        List<TaskRequest> taskRequestList = taskRequestRepository.findAll();
        assertThat(taskRequestList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TaskRequest in Elasticsearch
        verify(mockTaskRequestSearchRepository, times(0)).save(taskRequest);
    }

    @Test
    @Transactional
    public void deleteTaskRequest() throws Exception {
        // Initialize the database
        taskRequestRepository.saveAndFlush(taskRequest);

        int databaseSizeBeforeDelete = taskRequestRepository.findAll().size();

        // Get the taskRequest
        restTaskRequestMockMvc.perform(delete("/api/task-requests/{id}", taskRequest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TaskRequest> taskRequestList = taskRequestRepository.findAll();
        assertThat(taskRequestList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TaskRequest in Elasticsearch
        verify(mockTaskRequestSearchRepository, times(1)).deleteById(taskRequest.getId());
    }

    @Test
    @Transactional
    public void searchTaskRequest() throws Exception {
        // Initialize the database
        taskRequestRepository.saveAndFlush(taskRequest);
        when(mockTaskRequestSearchRepository.search(queryStringQuery("id:" + taskRequest.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(taskRequest), PageRequest.of(0, 1), 1));
        // Search the taskRequest
        restTaskRequestMockMvc.perform(get("/api/_search/task-requests?query=id:" + taskRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskRequest.class);
        TaskRequest taskRequest1 = new TaskRequest();
        taskRequest1.setId(1L);
        TaskRequest taskRequest2 = new TaskRequest();
        taskRequest2.setId(taskRequest1.getId());
        assertThat(taskRequest1).isEqualTo(taskRequest2);
        taskRequest2.setId(2L);
        assertThat(taskRequest1).isNotEqualTo(taskRequest2);
        taskRequest1.setId(null);
        assertThat(taskRequest1).isNotEqualTo(taskRequest2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskRequestDTO.class);
        TaskRequestDTO taskRequestDTO1 = new TaskRequestDTO();
        taskRequestDTO1.setId(1L);
        TaskRequestDTO taskRequestDTO2 = new TaskRequestDTO();
        assertThat(taskRequestDTO1).isNotEqualTo(taskRequestDTO2);
        taskRequestDTO2.setId(taskRequestDTO1.getId());
        assertThat(taskRequestDTO1).isEqualTo(taskRequestDTO2);
        taskRequestDTO2.setId(2L);
        assertThat(taskRequestDTO1).isNotEqualTo(taskRequestDTO2);
        taskRequestDTO1.setId(null);
        assertThat(taskRequestDTO1).isNotEqualTo(taskRequestDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(taskRequestMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(taskRequestMapper.fromId(null)).isNull();
    }
}
