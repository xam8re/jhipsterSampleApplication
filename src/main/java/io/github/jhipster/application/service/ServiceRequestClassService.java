package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.ServiceRequestClassDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ServiceRequestClass.
 */
public interface ServiceRequestClassService {

    /**
     * Save a serviceRequestClass.
     *
     * @param serviceRequestClassDTO the entity to save
     * @return the persisted entity
     */
    ServiceRequestClassDTO save(ServiceRequestClassDTO serviceRequestClassDTO);

    /**
     * Get all the serviceRequestClasses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ServiceRequestClassDTO> findAll(Pageable pageable);


    /**
     * Get the "id" serviceRequestClass.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ServiceRequestClassDTO> findOne(Long id);

    /**
     * Delete the "id" serviceRequestClass.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the serviceRequestClass corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ServiceRequestClassDTO> search(String query, Pageable pageable);
}
