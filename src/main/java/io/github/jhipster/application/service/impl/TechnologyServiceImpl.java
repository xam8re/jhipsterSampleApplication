package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.TechnologyService;
import io.github.jhipster.application.domain.Technology;
import io.github.jhipster.application.repository.TechnologyRepository;
import io.github.jhipster.application.repository.search.TechnologySearchRepository;
import io.github.jhipster.application.service.dto.TechnologyDTO;
import io.github.jhipster.application.service.mapper.TechnologyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Technology.
 */
@Service
@Transactional
public class TechnologyServiceImpl implements TechnologyService {

    private final Logger log = LoggerFactory.getLogger(TechnologyServiceImpl.class);

    private final TechnologyRepository technologyRepository;

    private final TechnologyMapper technologyMapper;

    private final TechnologySearchRepository technologySearchRepository;

    public TechnologyServiceImpl(TechnologyRepository technologyRepository, TechnologyMapper technologyMapper, TechnologySearchRepository technologySearchRepository) {
        this.technologyRepository = technologyRepository;
        this.technologyMapper = technologyMapper;
        this.technologySearchRepository = technologySearchRepository;
    }

    /**
     * Save a technology.
     *
     * @param technologyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TechnologyDTO save(TechnologyDTO technologyDTO) {
        log.debug("Request to save Technology : {}", technologyDTO);
        Technology technology = technologyMapper.toEntity(technologyDTO);
        technology = technologyRepository.save(technology);
        TechnologyDTO result = technologyMapper.toDto(technology);
        technologySearchRepository.save(technology);
        return result;
    }

    /**
     * Get all the technologies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TechnologyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Technologies");
        return technologyRepository.findAll(pageable)
            .map(technologyMapper::toDto);
    }


    /**
     * Get one technology by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TechnologyDTO> findOne(Long id) {
        log.debug("Request to get Technology : {}", id);
        return technologyRepository.findById(id)
            .map(technologyMapper::toDto);
    }

    /**
     * Delete the technology by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Technology : {}", id);
        technologyRepository.deleteById(id);
        technologySearchRepository.deleteById(id);
    }

    /**
     * Search for the technology corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TechnologyDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Technologies for query {}", query);
        return technologySearchRepository.search(queryStringQuery(query), pageable)
            .map(technologyMapper::toDto);
    }
}
