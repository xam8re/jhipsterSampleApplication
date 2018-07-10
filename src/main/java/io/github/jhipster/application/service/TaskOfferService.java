package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.TaskOfferDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TaskOffer.
 */
public interface TaskOfferService {

    /**
     * Save a taskOffer.
     *
     * @param taskOfferDTO the entity to save
     * @return the persisted entity
     */
    TaskOfferDTO save(TaskOfferDTO taskOfferDTO);

    /**
     * Get all the taskOffers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TaskOfferDTO> findAll(Pageable pageable);


    /**
     * Get the "id" taskOffer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TaskOfferDTO> findOne(Long id);

    /**
     * Delete the "id" taskOffer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the taskOffer corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TaskOfferDTO> search(String query, Pageable pageable);
}
