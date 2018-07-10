package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.ServiceOrderDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ServiceOrder.
 */
public interface ServiceOrderService {

    /**
     * Save a serviceOrder.
     *
     * @param serviceOrderDTO the entity to save
     * @return the persisted entity
     */
    ServiceOrderDTO save(ServiceOrderDTO serviceOrderDTO);

    /**
     * Get all the serviceOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ServiceOrderDTO> findAll(Pageable pageable);


    /**
     * Get the "id" serviceOrder.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ServiceOrderDTO> findOne(Long id);

    /**
     * Delete the "id" serviceOrder.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the serviceOrder corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ServiceOrderDTO> search(String query, Pageable pageable);
}
