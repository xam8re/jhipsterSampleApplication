package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.TwitterKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TwitterKey.
 */
public interface TwitterKeyService {

    /**
     * Save a twitterKey.
     *
     * @param twitterKey the entity to save
     * @return the persisted entity
     */
    TwitterKey save(TwitterKey twitterKey);

    /**
     * Get all the twitterKeys.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TwitterKey> findAll(Pageable pageable);

    /**
     * Get the "id" twitterKey.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TwitterKey findOne(Long id);

    /**
     * Delete the "id" twitterKey.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the twitterKey corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TwitterKey> search(String query, Pageable pageable);
}
