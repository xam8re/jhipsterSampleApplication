package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.TweetCategoryService;
import io.github.jhipster.application.domain.TweetCategory;
import io.github.jhipster.application.repository.TweetCategoryRepository;
import io.github.jhipster.application.repository.search.TweetCategorySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TweetCategory.
 */
@Service
@Transactional
public class TweetCategoryServiceImpl implements TweetCategoryService {

    private final Logger log = LoggerFactory.getLogger(TweetCategoryServiceImpl.class);

    private final TweetCategoryRepository tweetCategoryRepository;

    private final TweetCategorySearchRepository tweetCategorySearchRepository;

    public TweetCategoryServiceImpl(TweetCategoryRepository tweetCategoryRepository, TweetCategorySearchRepository tweetCategorySearchRepository) {
        this.tweetCategoryRepository = tweetCategoryRepository;
        this.tweetCategorySearchRepository = tweetCategorySearchRepository;
    }

    /**
     * Save a tweetCategory.
     *
     * @param tweetCategory the entity to save
     * @return the persisted entity
     */
    @Override
    public TweetCategory save(TweetCategory tweetCategory) {
        log.debug("Request to save TweetCategory : {}", tweetCategory);
        TweetCategory result = tweetCategoryRepository.save(tweetCategory);
        tweetCategorySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the tweetCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TweetCategory> findAll(Pageable pageable) {
        log.debug("Request to get all TweetCategories");
        return tweetCategoryRepository.findAll(pageable);
    }

    /**
     * Get one tweetCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TweetCategory findOne(Long id) {
        log.debug("Request to get TweetCategory : {}", id);
        return tweetCategoryRepository.findOne(id);
    }

    /**
     * Delete the tweetCategory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TweetCategory : {}", id);
        tweetCategoryRepository.delete(id);
        tweetCategorySearchRepository.delete(id);
    }

    /**
     * Search for the tweetCategory corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TweetCategory> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TweetCategories for query {}", query);
        Page<TweetCategory> result = tweetCategorySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
