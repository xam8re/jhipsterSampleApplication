package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.TaskOfferService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.TaskOfferDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TaskOffer.
 */
@RestController
@RequestMapping("/api")
public class TaskOfferResource {

    private final Logger log = LoggerFactory.getLogger(TaskOfferResource.class);

    private static final String ENTITY_NAME = "taskOffer";

    private final TaskOfferService taskOfferService;

    public TaskOfferResource(TaskOfferService taskOfferService) {
        this.taskOfferService = taskOfferService;
    }

    /**
     * POST  /task-offers : Create a new taskOffer.
     *
     * @param taskOfferDTO the taskOfferDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taskOfferDTO, or with status 400 (Bad Request) if the taskOffer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/task-offers")
    @Timed
    public ResponseEntity<TaskOfferDTO> createTaskOffer(@Valid @RequestBody TaskOfferDTO taskOfferDTO) throws URISyntaxException {
        log.debug("REST request to save TaskOffer : {}", taskOfferDTO);
        if (taskOfferDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskOffer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskOfferDTO result = taskOfferService.save(taskOfferDTO);
        return ResponseEntity.created(new URI("/api/task-offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /task-offers : Updates an existing taskOffer.
     *
     * @param taskOfferDTO the taskOfferDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taskOfferDTO,
     * or with status 400 (Bad Request) if the taskOfferDTO is not valid,
     * or with status 500 (Internal Server Error) if the taskOfferDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/task-offers")
    @Timed
    public ResponseEntity<TaskOfferDTO> updateTaskOffer(@Valid @RequestBody TaskOfferDTO taskOfferDTO) throws URISyntaxException {
        log.debug("REST request to update TaskOffer : {}", taskOfferDTO);
        if (taskOfferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskOfferDTO result = taskOfferService.save(taskOfferDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taskOfferDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /task-offers : get all the taskOffers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taskOffers in body
     */
    @GetMapping("/task-offers")
    @Timed
    public ResponseEntity<List<TaskOfferDTO>> getAllTaskOffers(Pageable pageable) {
        log.debug("REST request to get a page of TaskOffers");
        Page<TaskOfferDTO> page = taskOfferService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/task-offers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /task-offers/:id : get the "id" taskOffer.
     *
     * @param id the id of the taskOfferDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskOfferDTO, or with status 404 (Not Found)
     */
    @GetMapping("/task-offers/{id}")
    @Timed
    public ResponseEntity<TaskOfferDTO> getTaskOffer(@PathVariable Long id) {
        log.debug("REST request to get TaskOffer : {}", id);
        Optional<TaskOfferDTO> taskOfferDTO = taskOfferService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskOfferDTO);
    }

    /**
     * DELETE  /task-offers/:id : delete the "id" taskOffer.
     *
     * @param id the id of the taskOfferDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/task-offers/{id}")
    @Timed
    public ResponseEntity<Void> deleteTaskOffer(@PathVariable Long id) {
        log.debug("REST request to delete TaskOffer : {}", id);
        taskOfferService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/task-offers?query=:query : search for the taskOffer corresponding
     * to the query.
     *
     * @param query the query of the taskOffer search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/task-offers")
    @Timed
    public ResponseEntity<List<TaskOfferDTO>> searchTaskOffers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TaskOffers for query {}", query);
        Page<TaskOfferDTO> page = taskOfferService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/task-offers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
