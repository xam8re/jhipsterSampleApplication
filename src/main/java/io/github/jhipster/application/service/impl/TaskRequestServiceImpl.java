package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.TaskRequestService;
import io.github.jhipster.application.domain.TaskRequest;
import io.github.jhipster.application.repository.TaskRequestRepository;
import io.github.jhipster.application.repository.search.TaskRequestSearchRepository;
import io.github.jhipster.application.service.dto.TaskRequestDTO;
import io.github.jhipster.application.service.mapper.TaskRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TaskRequest.
 */
@Service
@Transactional
public class TaskRequestServiceImpl implements TaskRequestService {

    private final Logger log = LoggerFactory.getLogger(TaskRequestServiceImpl.class);

    private final TaskRequestRepository taskRequestRepository;

    private final TaskRequestMapper taskRequestMapper;

    private final TaskRequestSearchRepository taskRequestSearchRepository;

    public TaskRequestServiceImpl(TaskRequestRepository taskRequestRepository, TaskRequestMapper taskRequestMapper, TaskRequestSearchRepository taskRequestSearchRepository) {
        this.taskRequestRepository = taskRequestRepository;
        this.taskRequestMapper = taskRequestMapper;
        this.taskRequestSearchRepository = taskRequestSearchRepository;
    }

    /**
     * Save a taskRequest.
     *
     * @param taskRequestDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TaskRequestDTO save(TaskRequestDTO taskRequestDTO) {
        log.debug("Request to save TaskRequest : {}", taskRequestDTO);
        TaskRequest taskRequest = taskRequestMapper.toEntity(taskRequestDTO);
        taskRequest = taskRequestRepository.save(taskRequest);
        TaskRequestDTO result = taskRequestMapper.toDto(taskRequest);
        taskRequestSearchRepository.save(taskRequest);
        return result;
    }

    /**
     * Get all the taskRequests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TaskRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskRequests");
        return taskRequestRepository.findAll(pageable)
            .map(taskRequestMapper::toDto);
    }


    /**
     * Get one taskRequest by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TaskRequestDTO> findOne(Long id) {
        log.debug("Request to get TaskRequest : {}", id);
        return taskRequestRepository.findById(id)
            .map(taskRequestMapper::toDto);
    }

    /**
     * Delete the taskRequest by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaskRequest : {}", id);
        taskRequestRepository.deleteById(id);
        taskRequestSearchRepository.deleteById(id);
    }

    /**
     * Search for the taskRequest corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TaskRequestDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TaskRequests for query {}", query);
        return taskRequestSearchRepository.search(queryStringQuery(query), pageable)
            .map(taskRequestMapper::toDto);
    }
}
