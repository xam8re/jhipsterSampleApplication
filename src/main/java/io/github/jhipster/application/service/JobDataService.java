package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.JobData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing JobData.
 */
public interface JobDataService {

    /**
     * Save a jobData.
     *
     * @param jobData the entity to save
     * @return the persisted entity
     */
    JobData save(JobData jobData);

    /**
     * Get all the jobData.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<JobData> findAll(Pageable pageable);

    /**
     * Get the "id" jobData.
     *
     * @param id the id of the entity
     * @return the entity
     */
    JobData findOne(Long id);

    /**
     * Delete the "id" jobData.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the jobData corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<JobData> search(String query, Pageable pageable);
}
