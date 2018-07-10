package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.DimensionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Dimension.
 */
public interface DimensionService {

    /**
     * Save a dimension.
     *
     * @param dimensionDTO the entity to save
     * @return the persisted entity
     */
    DimensionDTO save(DimensionDTO dimensionDTO);

    /**
     * Get all the dimensions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DimensionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" dimension.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DimensionDTO> findOne(Long id);

    /**
     * Delete the "id" dimension.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the dimension corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DimensionDTO> search(String query, Pageable pageable);
}
