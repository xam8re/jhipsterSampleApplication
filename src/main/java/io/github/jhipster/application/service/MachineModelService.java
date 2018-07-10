package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.MachineModelDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MachineModel.
 */
public interface MachineModelService {

    /**
     * Save a machineModel.
     *
     * @param machineModelDTO the entity to save
     * @return the persisted entity
     */
    MachineModelDTO save(MachineModelDTO machineModelDTO);

    /**
     * Get all the machineModels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MachineModelDTO> findAll(Pageable pageable);


    /**
     * Get the "id" machineModel.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MachineModelDTO> findOne(Long id);

    /**
     * Delete the "id" machineModel.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the machineModel corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MachineModelDTO> search(String query, Pageable pageable);
}
