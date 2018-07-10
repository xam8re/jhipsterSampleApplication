package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.TaskOrderDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TaskOrder.
 */
public interface TaskOrderService {

    /**
     * Save a taskOrder.
     *
     * @param taskOrderDTO the entity to save
     * @return the persisted entity
     */
    TaskOrderDTO save(TaskOrderDTO taskOrderDTO);

    /**
     * Get all the taskOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TaskOrderDTO> findAll(Pageable pageable);


    /**
     * Get the "id" taskOrder.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TaskOrderDTO> findOne(Long id);

    /**
     * Delete the "id" taskOrder.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the taskOrder corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TaskOrderDTO> search(String query, Pageable pageable);
}
