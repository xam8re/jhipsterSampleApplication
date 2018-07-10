package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.TaskOrder;
import io.github.jhipster.application.repository.TaskOrderRepository;
import io.github.jhipster.application.repository.search.TaskOrderSearchRepository;
import io.github.jhipster.application.service.TaskOrderService;
import io.github.jhipster.application.service.dto.TaskOrderDTO;
import io.github.jhipster.application.service.mapper.TaskOrderMapper;
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
 * Test class for the TaskOrderResource REST controller.
 *
 * @see TaskOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class TaskOrderResourceIntTest {

    private static final OrderState DEFAULT_STATE = OrderState.PENDING;
    private static final OrderState UPDATED_STATE = OrderState.WIP;

    @Autowired
    private TaskOrderRepository taskOrderRepository;


    @Autowired
    private TaskOrderMapper taskOrderMapper;
    

    @Autowired
    private TaskOrderService taskOrderService;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.TaskOrderSearchRepositoryMockConfiguration
     */
    @Autowired
    private TaskOrderSearchRepository mockTaskOrderSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTaskOrderMockMvc;

    private TaskOrder taskOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaskOrderResource taskOrderResource = new TaskOrderResource(taskOrderService);
        this.restTaskOrderMockMvc = MockMvcBuilders.standaloneSetup(taskOrderResource)
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
    public static TaskOrder createEntity(EntityManager em) {
        TaskOrder taskOrder = new TaskOrder()
            .state(DEFAULT_STATE);
        return taskOrder;
    }

    @Before
    public void initTest() {
        taskOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskOrder() throws Exception {
        int databaseSizeBeforeCreate = taskOrderRepository.findAll().size();

        // Create the TaskOrder
        TaskOrderDTO taskOrderDTO = taskOrderMapper.toDto(taskOrder);
        restTaskOrderMockMvc.perform(post("/api/task-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the TaskOrder in the database
        List<TaskOrder> taskOrderList = taskOrderRepository.findAll();
        assertThat(taskOrderList).hasSize(databaseSizeBeforeCreate + 1);
        TaskOrder testTaskOrder = taskOrderList.get(taskOrderList.size() - 1);
        assertThat(testTaskOrder.getState()).isEqualTo(DEFAULT_STATE);

        // Validate the TaskOrder in Elasticsearch
        verify(mockTaskOrderSearchRepository, times(1)).save(testTaskOrder);
    }

    @Test
    @Transactional
    public void createTaskOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskOrderRepository.findAll().size();

        // Create the TaskOrder with an existing ID
        taskOrder.setId(1L);
        TaskOrderDTO taskOrderDTO = taskOrderMapper.toDto(taskOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskOrderMockMvc.perform(post("/api/task-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskOrder in the database
        List<TaskOrder> taskOrderList = taskOrderRepository.findAll();
        assertThat(taskOrderList).hasSize(databaseSizeBeforeCreate);

        // Validate the TaskOrder in Elasticsearch
        verify(mockTaskOrderSearchRepository, times(0)).save(taskOrder);
    }

    @Test
    @Transactional
    public void getAllTaskOrders() throws Exception {
        // Initialize the database
        taskOrderRepository.saveAndFlush(taskOrder);

        // Get all the taskOrderList
        restTaskOrderMockMvc.perform(get("/api/task-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }
    

    @Test
    @Transactional
    public void getTaskOrder() throws Exception {
        // Initialize the database
        taskOrderRepository.saveAndFlush(taskOrder);

        // Get the taskOrder
        restTaskOrderMockMvc.perform(get("/api/task-orders/{id}", taskOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taskOrder.getId().intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTaskOrder() throws Exception {
        // Get the taskOrder
        restTaskOrderMockMvc.perform(get("/api/task-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskOrder() throws Exception {
        // Initialize the database
        taskOrderRepository.saveAndFlush(taskOrder);

        int databaseSizeBeforeUpdate = taskOrderRepository.findAll().size();

        // Update the taskOrder
        TaskOrder updatedTaskOrder = taskOrderRepository.findById(taskOrder.getId()).get();
        // Disconnect from session so that the updates on updatedTaskOrder are not directly saved in db
        em.detach(updatedTaskOrder);
        updatedTaskOrder
            .state(UPDATED_STATE);
        TaskOrderDTO taskOrderDTO = taskOrderMapper.toDto(updatedTaskOrder);

        restTaskOrderMockMvc.perform(put("/api/task-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskOrderDTO)))
            .andExpect(status().isOk());

        // Validate the TaskOrder in the database
        List<TaskOrder> taskOrderList = taskOrderRepository.findAll();
        assertThat(taskOrderList).hasSize(databaseSizeBeforeUpdate);
        TaskOrder testTaskOrder = taskOrderList.get(taskOrderList.size() - 1);
        assertThat(testTaskOrder.getState()).isEqualTo(UPDATED_STATE);

        // Validate the TaskOrder in Elasticsearch
        verify(mockTaskOrderSearchRepository, times(1)).save(testTaskOrder);
    }

    @Test
    @Transactional
    public void updateNonExistingTaskOrder() throws Exception {
        int databaseSizeBeforeUpdate = taskOrderRepository.findAll().size();

        // Create the TaskOrder
        TaskOrderDTO taskOrderDTO = taskOrderMapper.toDto(taskOrder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTaskOrderMockMvc.perform(put("/api/task-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskOrder in the database
        List<TaskOrder> taskOrderList = taskOrderRepository.findAll();
        assertThat(taskOrderList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TaskOrder in Elasticsearch
        verify(mockTaskOrderSearchRepository, times(0)).save(taskOrder);
    }

    @Test
    @Transactional
    public void deleteTaskOrder() throws Exception {
        // Initialize the database
        taskOrderRepository.saveAndFlush(taskOrder);

        int databaseSizeBeforeDelete = taskOrderRepository.findAll().size();

        // Get the taskOrder
        restTaskOrderMockMvc.perform(delete("/api/task-orders/{id}", taskOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TaskOrder> taskOrderList = taskOrderRepository.findAll();
        assertThat(taskOrderList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TaskOrder in Elasticsearch
        verify(mockTaskOrderSearchRepository, times(1)).deleteById(taskOrder.getId());
    }

    @Test
    @Transactional
    public void searchTaskOrder() throws Exception {
        // Initialize the database
        taskOrderRepository.saveAndFlush(taskOrder);
        when(mockTaskOrderSearchRepository.search(queryStringQuery("id:" + taskOrder.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(taskOrder), PageRequest.of(0, 1), 1));
        // Search the taskOrder
        restTaskOrderMockMvc.perform(get("/api/_search/task-orders?query=id:" + taskOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskOrder.class);
        TaskOrder taskOrder1 = new TaskOrder();
        taskOrder1.setId(1L);
        TaskOrder taskOrder2 = new TaskOrder();
        taskOrder2.setId(taskOrder1.getId());
        assertThat(taskOrder1).isEqualTo(taskOrder2);
        taskOrder2.setId(2L);
        assertThat(taskOrder1).isNotEqualTo(taskOrder2);
        taskOrder1.setId(null);
        assertThat(taskOrder1).isNotEqualTo(taskOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskOrderDTO.class);
        TaskOrderDTO taskOrderDTO1 = new TaskOrderDTO();
        taskOrderDTO1.setId(1L);
        TaskOrderDTO taskOrderDTO2 = new TaskOrderDTO();
        assertThat(taskOrderDTO1).isNotEqualTo(taskOrderDTO2);
        taskOrderDTO2.setId(taskOrderDTO1.getId());
        assertThat(taskOrderDTO1).isEqualTo(taskOrderDTO2);
        taskOrderDTO2.setId(2L);
        assertThat(taskOrderDTO1).isNotEqualTo(taskOrderDTO2);
        taskOrderDTO1.setId(null);
        assertThat(taskOrderDTO1).isNotEqualTo(taskOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(taskOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(taskOrderMapper.fromId(null)).isNull();
    }
}
