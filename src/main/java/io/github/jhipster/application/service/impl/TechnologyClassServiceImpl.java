package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.TechnologyClassService;
import io.github.jhipster.application.domain.TechnologyClass;
import io.github.jhipster.application.repository.TechnologyClassRepository;
import io.github.jhipster.application.repository.search.TechnologyClassSearchRepository;
import io.github.jhipster.application.service.dto.TechnologyClassDTO;
import io.github.jhipster.application.service.mapper.TechnologyClassMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TechnologyClass.
 */
@Service
@Transactional
public class TechnologyClassServiceImpl implements TechnologyClassService {

    private final Logger log = LoggerFactory.getLogger(TechnologyClassServiceImpl.class);

    private final TechnologyClassRepository technologyClassRepository;

    private final TechnologyClassMapper technologyClassMapper;

    private final TechnologyClassSearchRepository technologyClassSearchRepository;

    public TechnologyClassServiceImpl(TechnologyClassRepository technologyClassRepository, TechnologyClassMapper technologyClassMapper, TechnologyClassSearchRepository technologyClassSearchRepository) {
        this.technologyClassRepository = technologyClassRepository;
        this.technologyClassMapper = technologyClassMapper;
        this.technologyClassSearchRepository = technologyClassSearchRepository;
    }

    /**
     * Save a technologyClass.
     *
     * @param technologyClassDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TechnologyClassDTO save(TechnologyClassDTO technologyClassDTO) {
        log.debug("Request to save TechnologyClass : {}", technologyClassDTO);
        TechnologyClass technologyClass = technologyClassMapper.toEntity(technologyClassDTO);
        technologyClass = technologyClassRepository.save(technologyClass);
        TechnologyClassDTO result = technologyClassMapper.toDto(technologyClass);
        technologyClassSearchRepository.save(technologyClass);
        return result;
    }

    /**
     * Get all the technologyClasses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TechnologyClassDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TechnologyClasses");
        return technologyClassRepository.findAll(pageable)
            .map(technologyClassMapper::toDto);
    }


    /**
     * Get one technologyClass by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TechnologyClassDTO> findOne(Long id) {
        log.debug("Request to get TechnologyClass : {}", id);
        return technologyClassRepository.findById(id)
            .map(technologyClassMapper::toDto);
    }

    /**
     * Delete the technologyClass by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TechnologyClass : {}", id);
        technologyClassRepository.deleteById(id);
        technologyClassSearchRepository.deleteById(id);
    }

    /**
     * Search for the technologyClass corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TechnologyClassDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TechnologyClasses for query {}", query);
        return technologyClassSearchRepository.search(queryStringQuery(query), pageable)
            .map(technologyClassMapper::toDto);
    }
}
