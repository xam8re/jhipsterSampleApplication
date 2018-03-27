package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.TweetCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TweetCategory.
 */
public interface TweetCategoryService {

    /**
     * Save a tweetCategory.
     *
     * @param tweetCategory the entity to save
     * @return the persisted entity
     */
    TweetCategory save(TweetCategory tweetCategory);

    /**
     * Get all the tweetCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TweetCategory> findAll(Pageable pageable);

    /**
     * Get the "id" tweetCategory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TweetCategory findOne(Long id);

    /**
     * Delete the "id" tweetCategory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tweetCategory corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TweetCategory> search(String query, Pageable pageable);
}
