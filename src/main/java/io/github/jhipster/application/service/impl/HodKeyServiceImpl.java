package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.HodKeyService;
import io.github.jhipster.application.domain.HodKey;
import io.github.jhipster.application.repository.HodKeyRepository;
import io.github.jhipster.application.repository.search.HodKeySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing HodKey.
 */
@Service
@Transactional
public class HodKeyServiceImpl implements HodKeyService {

    private final Logger log = LoggerFactory.getLogger(HodKeyServiceImpl.class);

    private final HodKeyRepository hodKeyRepository;

    private final HodKeySearchRepository hodKeySearchRepository;

    public HodKeyServiceImpl(HodKeyRepository hodKeyRepository, HodKeySearchRepository hodKeySearchRepository) {
        this.hodKeyRepository = hodKeyRepository;
        this.hodKeySearchRepository = hodKeySearchRepository;
    }

    /**
     * Save a hodKey.
     *
     * @param hodKey the entity to save
     * @return the persisted entity
     */
    @Override
    public HodKey save(HodKey hodKey) {
        log.debug("Request to save HodKey : {}", hodKey);
        HodKey result = hodKeyRepository.save(hodKey);
        hodKeySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the hodKeys.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HodKey> findAll(Pageable pageable) {
        log.debug("Request to get all HodKeys");
        return hodKeyRepository.findAll(pageable);
    }

    /**
     * Get one hodKey by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public HodKey findOne(Long id) {
        log.debug("Request to get HodKey : {}", id);
        return hodKeyRepository.findOne(id);
    }

    /**
     * Delete the hodKey by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HodKey : {}", id);
        hodKeyRepository.delete(id);
        hodKeySearchRepository.delete(id);
    }

    /**
     * Search for the hodKey corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HodKey> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of HodKeys for query {}", query);
        Page<HodKey> result = hodKeySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
