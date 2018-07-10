package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.TaskOrderService;
import io.github.jhipster.application.domain.TaskOrder;
import io.github.jhipster.application.repository.TaskOrderRepository;
import io.github.jhipster.application.repository.search.TaskOrderSearchRepository;
import io.github.jhipster.application.service.dto.TaskOrderDTO;
import io.github.jhipster.application.service.mapper.TaskOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TaskOrder.
 */
@Service
@Transactional
public class TaskOrderServiceImpl implements TaskOrderService {

    private final Logger log = LoggerFactory.getLogger(TaskOrderServiceImpl.class);

    private final TaskOrderRepository taskOrderRepository;

    private final TaskOrderMapper taskOrderMapper;

    private final TaskOrderSearchRepository taskOrderSearchRepository;

    public TaskOrderServiceImpl(TaskOrderRepository taskOrderRepository, TaskOrderMapper taskOrderMapper, TaskOrderSearchRepository taskOrderSearchRepository) {
        this.taskOrderRepository = taskOrderRepository;
        this.taskOrderMapper = taskOrderMapper;
        this.taskOrderSearchRepository = taskOrderSearchRepository;
    }

    /**
     * Save a taskOrder.
     *
     * @param taskOrderDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TaskOrderDTO save(TaskOrderDTO taskOrderDTO) {
        log.debug("Request to save TaskOrder : {}", taskOrderDTO);
        TaskOrder taskOrder = taskOrderMapper.toEntity(taskOrderDTO);
        taskOrder = taskOrderRepository.save(taskOrder);
        TaskOrderDTO result = taskOrderMapper.toDto(taskOrder);
        taskOrderSearchRepository.save(taskOrder);
        return result;
    }

    /**
     * Get all the taskOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TaskOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskOrders");
        return taskOrderRepository.findAll(pageable)
            .map(taskOrderMapper::toDto);
    }


    /**
     * Get one taskOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TaskOrderDTO> findOne(Long id) {
        log.debug("Request to get TaskOrder : {}", id);
        return taskOrderRepository.findById(id)
            .map(taskOrderMapper::toDto);
    }

    /**
     * Delete the taskOrder by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaskOrder : {}", id);
        taskOrderRepository.deleteById(id);
        taskOrderSearchRepository.deleteById(id);
    }

    /**
     * Search for the taskOrder corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TaskOrderDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TaskOrders for query {}", query);
        return taskOrderSearchRepository.search(queryStringQuery(query), pageable)
            .map(taskOrderMapper::toDto);
    }
}
