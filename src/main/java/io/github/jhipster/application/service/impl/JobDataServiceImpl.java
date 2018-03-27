package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.JobDataService;
import io.github.jhipster.application.domain.JobData;
import io.github.jhipster.application.repository.JobDataRepository;
import io.github.jhipster.application.repository.search.JobDataSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing JobData.
 */
@Service
@Transactional
public class JobDataServiceImpl implements JobDataService {

    private final Logger log = LoggerFactory.getLogger(JobDataServiceImpl.class);

    private final JobDataRepository jobDataRepository;

    private final JobDataSearchRepository jobDataSearchRepository;

    public JobDataServiceImpl(JobDataRepository jobDataRepository, JobDataSearchRepository jobDataSearchRepository) {
        this.jobDataRepository = jobDataRepository;
        this.jobDataSearchRepository = jobDataSearchRepository;
    }

    /**
     * Save a jobData.
     *
     * @param jobData the entity to save
     * @return the persisted entity
     */
    @Override
    public JobData save(JobData jobData) {
        log.debug("Request to save JobData : {}", jobData);
        JobData result = jobDataRepository.save(jobData);
        jobDataSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the jobData.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JobData> findAll(Pageable pageable) {
        log.debug("Request to get all JobData");
        return jobDataRepository.findAll(pageable);
    }

    /**
     * Get one jobData by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public JobData findOne(Long id) {
        log.debug("Request to get JobData : {}", id);
        return jobDataRepository.findOne(id);
    }

    /**
     * Delete the jobData by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete JobData : {}", id);
        jobDataRepository.delete(id);
        jobDataSearchRepository.delete(id);
    }

    /**
     * Search for the jobData corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JobData> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of JobData for query {}", query);
        Page<JobData> result = jobDataSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
