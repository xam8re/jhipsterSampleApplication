package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.HodKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing HodKey.
 */
public interface HodKeyService {

    /**
     * Save a hodKey.
     *
     * @param hodKey the entity to save
     * @return the persisted entity
     */
    HodKey save(HodKey hodKey);

    /**
     * Get all the hodKeys.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<HodKey> findAll(Pageable pageable);

    /**
     * Get the "id" hodKey.
     *
     * @param id the id of the entity
     * @return the entity
     */
    HodKey findOne(Long id);

    /**
     * Delete the "id" hodKey.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the hodKey corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<HodKey> search(String query, Pageable pageable);
}
