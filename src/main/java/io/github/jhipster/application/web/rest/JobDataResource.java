package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.JobData;
import io.github.jhipster.application.service.JobDataService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing JobData.
 */
@RestController
@RequestMapping("/api")
public class JobDataResource {

    private final Logger log = LoggerFactory.getLogger(JobDataResource.class);

    private static final String ENTITY_NAME = "jobData";

    private final JobDataService jobDataService;

    public JobDataResource(JobDataService jobDataService) {
        this.jobDataService = jobDataService;
    }

    /**
     * POST  /job-data : Create a new jobData.
     *
     * @param jobData the jobData to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobData, or with status 400 (Bad Request) if the jobData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-data")
    @Timed
    public ResponseEntity<JobData> createJobData(@RequestBody JobData jobData) throws URISyntaxException {
        log.debug("REST request to save JobData : {}", jobData);
        if (jobData.getId() != null) {
            throw new BadRequestAlertException("A new jobData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobData result = jobDataService.save(jobData);
        return ResponseEntity.created(new URI("/api/job-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-data : Updates an existing jobData.
     *
     * @param jobData the jobData to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobData,
     * or with status 400 (Bad Request) if the jobData is not valid,
     * or with status 500 (Internal Server Error) if the jobData couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-data")
    @Timed
    public ResponseEntity<JobData> updateJobData(@RequestBody JobData jobData) throws URISyntaxException {
        log.debug("REST request to update JobData : {}", jobData);
        if (jobData.getId() == null) {
            return createJobData(jobData);
        }
        JobData result = jobDataService.save(jobData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobData.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-data : get all the jobData.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of jobData in body
     */
    @GetMapping("/job-data")
    @Timed
    public ResponseEntity<List<JobData>> getAllJobData(Pageable pageable) {
        log.debug("REST request to get a page of JobData");
        Page<JobData> page = jobDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/job-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /job-data/:id : get the "id" jobData.
     *
     * @param id the id of the jobData to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobData, or with status 404 (Not Found)
     */
    @GetMapping("/job-data/{id}")
    @Timed
    public ResponseEntity<JobData> getJobData(@PathVariable Long id) {
        log.debug("REST request to get JobData : {}", id);
        JobData jobData = jobDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jobData));
    }

    /**
     * DELETE  /job-data/:id : delete the "id" jobData.
     *
     * @param id the id of the jobData to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-data/{id}")
    @Timed
    public ResponseEntity<Void> deleteJobData(@PathVariable Long id) {
        log.debug("REST request to delete JobData : {}", id);
        jobDataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/job-data?query=:query : search for the jobData corresponding
     * to the query.
     *
     * @param query the query of the jobData search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/job-data")
    @Timed
    public ResponseEntity<List<JobData>> searchJobData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of JobData for query {}", query);
        Page<JobData> page = jobDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/job-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
