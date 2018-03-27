package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.TweetService;
import io.github.jhipster.application.domain.Tweet;
import io.github.jhipster.application.repository.TweetRepository;
import io.github.jhipster.application.repository.search.TweetSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Tweet.
 */
@Service
@Transactional
public class TweetServiceImpl implements TweetService {

    private final Logger log = LoggerFactory.getLogger(TweetServiceImpl.class);

    private final TweetRepository tweetRepository;

    private final TweetSearchRepository tweetSearchRepository;

    public TweetServiceImpl(TweetRepository tweetRepository, TweetSearchRepository tweetSearchRepository) {
        this.tweetRepository = tweetRepository;
        this.tweetSearchRepository = tweetSearchRepository;
    }

    /**
     * Save a tweet.
     *
     * @param tweet the entity to save
     * @return the persisted entity
     */
    @Override
    public Tweet save(Tweet tweet) {
        log.debug("Request to save Tweet : {}", tweet);
        Tweet result = tweetRepository.save(tweet);
        tweetSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the tweets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Tweet> findAll(Pageable pageable) {
        log.debug("Request to get all Tweets");
        return tweetRepository.findAll(pageable);
    }

    /**
     * Get one tweet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Tweet findOne(Long id) {
        log.debug("Request to get Tweet : {}", id);
        return tweetRepository.findOne(id);
    }

    /**
     * Delete the tweet by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tweet : {}", id);
        tweetRepository.delete(id);
        tweetSearchRepository.delete(id);
    }

    /**
     * Search for the tweet corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Tweet> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tweets for query {}", query);
        Page<Tweet> result = tweetSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
