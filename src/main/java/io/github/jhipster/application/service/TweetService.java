package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Tweet.
 */
public interface TweetService {

    /**
     * Save a tweet.
     *
     * @param tweet the entity to save
     * @return the persisted entity
     */
    Tweet save(Tweet tweet);

    /**
     * Get all the tweets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Tweet> findAll(Pageable pageable);

    /**
     * Get the "id" tweet.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Tweet findOne(Long id);

    /**
     * Delete the "id" tweet.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tweet corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Tweet> search(String query, Pageable pageable);
}
