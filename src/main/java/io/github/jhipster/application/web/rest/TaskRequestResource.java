package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.TaskRequestService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.TaskRequestDTO;
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
 * REST controller for managing TaskRequest.
 */
@RestController
@RequestMapping("/api")
public class TaskRequestResource {

    private final Logger log = LoggerFactory.getLogger(TaskRequestResource.class);

    private static final String ENTITY_NAME = "taskRequest";

    private final TaskRequestService taskRequestService;

    public TaskRequestResource(TaskRequestService taskRequestService) {
        this.taskRequestService = taskRequestService;
    }

    /**
     * POST  /task-requests : Create a new taskRequest.
     *
     * @param taskRequestDTO the taskRequestDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taskRequestDTO, or with status 400 (Bad Request) if the taskRequest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/task-requests")
    @Timed
    public ResponseEntity<TaskRequestDTO> createTaskRequest(@RequestBody TaskRequestDTO taskRequestDTO) throws URISyntaxException {
        log.debug("REST request to save TaskRequest : {}", taskRequestDTO);
        if (taskRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskRequestDTO result = taskRequestService.save(taskRequestDTO);
        return ResponseEntity.created(new URI("/api/task-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /task-requests : Updates an existing taskRequest.
     *
     * @param taskRequestDTO the taskRequestDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taskRequestDTO,
     * or with status 400 (Bad Request) if the taskRequestDTO is not valid,
     * or with status 500 (Internal Server Error) if the taskRequestDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/task-requests")
    @Timed
    public ResponseEntity<TaskRequestDTO> updateTaskRequest(@RequestBody TaskRequestDTO taskRequestDTO) throws URISyntaxException {
        log.debug("REST request to update TaskRequest : {}", taskRequestDTO);
        if (taskRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskRequestDTO result = taskRequestService.save(taskRequestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taskRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /task-requests : get all the taskRequests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taskRequests in body
     */
    @GetMapping("/task-requests")
    @Timed
    public ResponseEntity<List<TaskRequestDTO>> getAllTaskRequests(Pageable pageable) {
        log.debug("REST request to get a page of TaskRequests");
        Page<TaskRequestDTO> page = taskRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/task-requests");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /task-requests/:id : get the "id" taskRequest.
     *
     * @param id the id of the taskRequestDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskRequestDTO, or with status 404 (Not Found)
     */
    @GetMapping("/task-requests/{id}")
    @Timed
    public ResponseEntity<TaskRequestDTO> getTaskRequest(@PathVariable Long id) {
        log.debug("REST request to get TaskRequest : {}", id);
        Optional<TaskRequestDTO> taskRequestDTO = taskRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskRequestDTO);
    }

    /**
     * DELETE  /task-requests/:id : delete the "id" taskRequest.
     *
     * @param id the id of the taskRequestDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/task-requests/{id}")
    @Timed
    public ResponseEntity<Void> deleteTaskRequest(@PathVariable Long id) {
        log.debug("REST request to delete TaskRequest : {}", id);
        taskRequestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/task-requests?query=:query : search for the taskRequest corresponding
     * to the query.
     *
     * @param query the query of the taskRequest search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/task-requests")
    @Timed
    public ResponseEntity<List<TaskRequestDTO>> searchTaskRequests(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TaskRequests for query {}", query);
        Page<TaskRequestDTO> page = taskRequestService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/task-requests");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
