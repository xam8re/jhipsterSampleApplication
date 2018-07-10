package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.TechnologyClassDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TechnologyClass.
 */
public interface TechnologyClassService {

    /**
     * Save a technologyClass.
     *
     * @param technologyClassDTO the entity to save
     * @return the persisted entity
     */
    TechnologyClassDTO save(TechnologyClassDTO technologyClassDTO);

    /**
     * Get all the technologyClasses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TechnologyClassDTO> findAll(Pageable pageable);


    /**
     * Get the "id" technologyClass.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TechnologyClassDTO> findOne(Long id);

    /**
     * Delete the "id" technologyClass.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the technologyClass corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TechnologyClassDTO> search(String query, Pageable pageable);
}
