package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.TechnologyDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Technology.
 */
public interface TechnologyService {

    /**
     * Save a technology.
     *
     * @param technologyDTO the entity to save
     * @return the persisted entity
     */
    TechnologyDTO save(TechnologyDTO technologyDTO);

    /**
     * Get all the technologies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TechnologyDTO> findAll(Pageable pageable);


    /**
     * Get the "id" technology.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TechnologyDTO> findOne(Long id);

    /**
     * Delete the "id" technology.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the technology corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TechnologyDTO> search(String query, Pageable pageable);
}
