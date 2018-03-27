package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.TweetTemplateService;
import io.github.jhipster.application.domain.TweetTemplate;
import io.github.jhipster.application.repository.TweetTemplateRepository;
import io.github.jhipster.application.repository.search.TweetTemplateSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TweetTemplate.
 */
@Service
@Transactional
public class TweetTemplateServiceImpl implements TweetTemplateService {

    private final Logger log = LoggerFactory.getLogger(TweetTemplateServiceImpl.class);

    private final TweetTemplateRepository tweetTemplateRepository;

    private final TweetTemplateSearchRepository tweetTemplateSearchRepository;

    public TweetTemplateServiceImpl(TweetTemplateRepository tweetTemplateRepository, TweetTemplateSearchRepository tweetTemplateSearchRepository) {
        this.tweetTemplateRepository = tweetTemplateRepository;
        this.tweetTemplateSearchRepository = tweetTemplateSearchRepository;
    }

    /**
     * Save a tweetTemplate.
     *
     * @param tweetTemplate the entity to save
     * @return the persisted entity
     */
    @Override
    public TweetTemplate save(TweetTemplate tweetTemplate) {
        log.debug("Request to save TweetTemplate : {}", tweetTemplate);
        TweetTemplate result = tweetTemplateRepository.save(tweetTemplate);
        tweetTemplateSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the tweetTemplates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TweetTemplate> findAll(Pageable pageable) {
        log.debug("Request to get all TweetTemplates");
        return tweetTemplateRepository.findAll(pageable);
    }

    /**
     * Get one tweetTemplate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TweetTemplate findOne(Long id) {
        log.debug("Request to get TweetTemplate : {}", id);
        return tweetTemplateRepository.findOne(id);
    }

    /**
     * Delete the tweetTemplate by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TweetTemplate : {}", id);
        tweetTemplateRepository.delete(id);
        tweetTemplateSearchRepository.delete(id);
    }

    /**
     * Search for the tweetTemplate corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TweetTemplate> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TweetTemplates for query {}", query);
        Page<TweetTemplate> result = tweetTemplateSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
