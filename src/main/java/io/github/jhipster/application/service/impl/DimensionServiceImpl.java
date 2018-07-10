package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.DimensionService;
import io.github.jhipster.application.domain.Dimension;
import io.github.jhipster.application.repository.DimensionRepository;
import io.github.jhipster.application.repository.search.DimensionSearchRepository;
import io.github.jhipster.application.service.dto.DimensionDTO;
import io.github.jhipster.application.service.mapper.DimensionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Dimension.
 */
@Service
@Transactional
public class DimensionServiceImpl implements DimensionService {

    private final Logger log = LoggerFactory.getLogger(DimensionServiceImpl.class);

    private final DimensionRepository dimensionRepository;

    private final DimensionMapper dimensionMapper;

    private final DimensionSearchRepository dimensionSearchRepository;

    public DimensionServiceImpl(DimensionRepository dimensionRepository, DimensionMapper dimensionMapper, DimensionSearchRepository dimensionSearchRepository) {
        this.dimensionRepository = dimensionRepository;
        this.dimensionMapper = dimensionMapper;
        this.dimensionSearchRepository = dimensionSearchRepository;
    }

    /**
     * Save a dimension.
     *
     * @param dimensionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DimensionDTO save(DimensionDTO dimensionDTO) {
        log.debug("Request to save Dimension : {}", dimensionDTO);
        Dimension dimension = dimensionMapper.toEntity(dimensionDTO);
        dimension = dimensionRepository.save(dimension);
        DimensionDTO result = dimensionMapper.toDto(dimension);
        dimensionSearchRepository.save(dimension);
        return result;
    }

    /**
     * Get all the dimensions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DimensionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dimensions");
        return dimensionRepository.findAll(pageable)
            .map(dimensionMapper::toDto);
    }


    /**
     * Get one dimension by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DimensionDTO> findOne(Long id) {
        log.debug("Request to get Dimension : {}", id);
        return dimensionRepository.findById(id)
            .map(dimensionMapper::toDto);
    }

    /**
     * Delete the dimension by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dimension : {}", id);
        dimensionRepository.deleteById(id);
        dimensionSearchRepository.deleteById(id);
    }

    /**
     * Search for the dimension corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DimensionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Dimensions for query {}", query);
        return dimensionSearchRepository.search(queryStringQuery(query), pageable)
            .map(dimensionMapper::toDto);
    }
}
