package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.TwitterKeyService;
import io.github.jhipster.application.domain.TwitterKey;
import io.github.jhipster.application.repository.TwitterKeyRepository;
import io.github.jhipster.application.repository.search.TwitterKeySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TwitterKey.
 */
@Service
@Transactional
public class TwitterKeyServiceImpl implements TwitterKeyService {

    private final Logger log = LoggerFactory.getLogger(TwitterKeyServiceImpl.class);

    private final TwitterKeyRepository twitterKeyRepository;

    private final TwitterKeySearchRepository twitterKeySearchRepository;

    public TwitterKeyServiceImpl(TwitterKeyRepository twitterKeyRepository, TwitterKeySearchRepository twitterKeySearchRepository) {
        this.twitterKeyRepository = twitterKeyRepository;
        this.twitterKeySearchRepository = twitterKeySearchRepository;
    }

    /**
     * Save a twitterKey.
     *
     * @param twitterKey the entity to save
     * @return the persisted entity
     */
    @Override
    public TwitterKey save(TwitterKey twitterKey) {
        log.debug("Request to save TwitterKey : {}", twitterKey);
        TwitterKey result = twitterKeyRepository.save(twitterKey);
        twitterKeySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the twitterKeys.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TwitterKey> findAll(Pageable pageable) {
        log.debug("Request to get all TwitterKeys");
        return twitterKeyRepository.findAll(pageable);
    }

    /**
     * Get one twitterKey by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TwitterKey findOne(Long id) {
        log.debug("Request to get TwitterKey : {}", id);
        return twitterKeyRepository.findOne(id);
    }

    /**
     * Delete the twitterKey by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TwitterKey : {}", id);
        twitterKeyRepository.delete(id);
        twitterKeySearchRepository.delete(id);
    }

    /**
     * Search for the twitterKey corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TwitterKey> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TwitterKeys for query {}", query);
        Page<TwitterKey> result = twitterKeySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
