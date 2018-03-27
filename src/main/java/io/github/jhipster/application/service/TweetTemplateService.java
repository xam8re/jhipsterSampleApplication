package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.TweetTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TweetTemplate.
 */
public interface TweetTemplateService {

    /**
     * Save a tweetTemplate.
     *
     * @param tweetTemplate the entity to save
     * @return the persisted entity
     */
    TweetTemplate save(TweetTemplate tweetTemplate);

    /**
     * Get all the tweetTemplates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TweetTemplate> findAll(Pageable pageable);

    /**
     * Get the "id" tweetTemplate.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TweetTemplate findOne(Long id);

    /**
     * Delete the "id" tweetTemplate.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tweetTemplate corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TweetTemplate> search(String query, Pageable pageable);
}
