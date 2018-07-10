package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.AMSAUserDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AMSAUser.
 */
public interface AMSAUserService {

    /**
     * Save a aMSAUser.
     *
     * @param aMSAUserDTO the entity to save
     * @return the persisted entity
     */
    AMSAUserDTO save(AMSAUserDTO aMSAUserDTO);

    /**
     * Get all the aMSAUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AMSAUserDTO> findAll(Pageable pageable);


    /**
     * Get the "id" aMSAUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AMSAUserDTO> findOne(Long id);

    /**
     * Delete the "id" aMSAUser.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the aMSAUser corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AMSAUserDTO> search(String query, Pageable pageable);
}
