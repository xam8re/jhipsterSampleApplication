package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.OrderHistoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing OrderHistory.
 */
public interface OrderHistoryService {

    /**
     * Save a orderHistory.
     *
     * @param orderHistoryDTO the entity to save
     * @return the persisted entity
     */
    OrderHistoryDTO save(OrderHistoryDTO orderHistoryDTO);

    /**
     * Get all the orderHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OrderHistoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" orderHistory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OrderHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" orderHistory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the orderHistory corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OrderHistoryDTO> search(String query, Pageable pageable);
}
