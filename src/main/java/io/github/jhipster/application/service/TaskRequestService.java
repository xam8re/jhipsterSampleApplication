package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.TaskRequestDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TaskRequest.
 */
public interface TaskRequestService {

    /**
     * Save a taskRequest.
     *
     * @param taskRequestDTO the entity to save
     * @return the persisted entity
     */
    TaskRequestDTO save(TaskRequestDTO taskRequestDTO);

    /**
     * Get all the taskRequests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TaskRequestDTO> findAll(Pageable pageable);


    /**
     * Get the "id" taskRequest.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TaskRequestDTO> findOne(Long id);

    /**
     * Delete the "id" taskRequest.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the taskRequest corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TaskRequestDTO> search(String query, Pageable pageable);
}
