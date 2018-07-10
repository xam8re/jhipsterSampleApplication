package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.TaskOrderService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.TaskOrderDTO;
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
 * REST controller for managing TaskOrder.
 */
@RestController
@RequestMapping("/api")
public class TaskOrderResource {

    private final Logger log = LoggerFactory.getLogger(TaskOrderResource.class);

    private static final String ENTITY_NAME = "taskOrder";

    private final TaskOrderService taskOrderService;

    public TaskOrderResource(TaskOrderService taskOrderService) {
        this.taskOrderService = taskOrderService;
    }

    /**
     * POST  /task-orders : Create a new taskOrder.
     *
     * @param taskOrderDTO the taskOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taskOrderDTO, or with status 400 (Bad Request) if the taskOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/task-orders")
    @Timed
    public ResponseEntity<TaskOrderDTO> createTaskOrder(@RequestBody TaskOrderDTO taskOrderDTO) throws URISyntaxException {
        log.debug("REST request to save TaskOrder : {}", taskOrderDTO);
        if (taskOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskOrderDTO result = taskOrderService.save(taskOrderDTO);
        return ResponseEntity.created(new URI("/api/task-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /task-orders : Updates an existing taskOrder.
     *
     * @param taskOrderDTO the taskOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taskOrderDTO,
     * or with status 400 (Bad Request) if the taskOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the taskOrderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/task-orders")
    @Timed
    public ResponseEntity<TaskOrderDTO> updateTaskOrder(@RequestBody TaskOrderDTO taskOrderDTO) throws URISyntaxException {
        log.debug("REST request to update TaskOrder : {}", taskOrderDTO);
        if (taskOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskOrderDTO result = taskOrderService.save(taskOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taskOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /task-orders : get all the taskOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taskOrders in body
     */
    @GetMapping("/task-orders")
    @Timed
    public ResponseEntity<List<TaskOrderDTO>> getAllTaskOrders(Pageable pageable) {
        log.debug("REST request to get a page of TaskOrders");
        Page<TaskOrderDTO> page = taskOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/task-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /task-orders/:id : get the "id" taskOrder.
     *
     * @param id the id of the taskOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/task-orders/{id}")
    @Timed
    public ResponseEntity<TaskOrderDTO> getTaskOrder(@PathVariable Long id) {
        log.debug("REST request to get TaskOrder : {}", id);
        Optional<TaskOrderDTO> taskOrderDTO = taskOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskOrderDTO);
    }

    /**
     * DELETE  /task-orders/:id : delete the "id" taskOrder.
     *
     * @param id the id of the taskOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/task-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteTaskOrder(@PathVariable Long id) {
        log.debug("REST request to delete TaskOrder : {}", id);
        taskOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/task-orders?query=:query : search for the taskOrder corresponding
     * to the query.
     *
     * @param query the query of the taskOrder search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/task-orders")
    @Timed
    public ResponseEntity<List<TaskOrderDTO>> searchTaskOrders(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TaskOrders for query {}", query);
        Page<TaskOrderDTO> page = taskOrderService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/task-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
