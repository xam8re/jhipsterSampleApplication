package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.ServiceOfferDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ServiceOffer.
 */
public interface ServiceOfferService {

    /**
     * Save a serviceOffer.
     *
     * @param serviceOfferDTO the entity to save
     * @return the persisted entity
     */
    ServiceOfferDTO save(ServiceOfferDTO serviceOfferDTO);

    /**
     * Get all the serviceOffers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ServiceOfferDTO> findAll(Pageable pageable);


    /**
     * Get the "id" serviceOffer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ServiceOfferDTO> findOne(Long id);

    /**
     * Delete the "id" serviceOffer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the serviceOffer corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ServiceOfferDTO> search(String query, Pageable pageable);
}
