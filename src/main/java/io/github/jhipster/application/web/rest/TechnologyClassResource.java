package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.TechnologyClassService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.TechnologyClassDTO;
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
 * REST controller for managing TechnologyClass.
 */
@RestController
@RequestMapping("/api")
public class TechnologyClassResource {

    private final Logger log = LoggerFactory.getLogger(TechnologyClassResource.class);

    private static final String ENTITY_NAME = "technologyClass";

    private final TechnologyClassService technologyClassService;

    public TechnologyClassResource(TechnologyClassService technologyClassService) {
        this.technologyClassService = technologyClassService;
    }

    /**
     * POST  /technology-classes : Create a new technologyClass.
     *
     * @param technologyClassDTO the technologyClassDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new technologyClassDTO, or with status 400 (Bad Request) if the technologyClass has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/technology-classes")
    @Timed
    public ResponseEntity<TechnologyClassDTO> createTechnologyClass(@Valid @RequestBody TechnologyClassDTO technologyClassDTO) throws URISyntaxException {
        log.debug("REST request to save TechnologyClass : {}", technologyClassDTO);
        if (technologyClassDTO.getId() != null) {
            throw new BadRequestAlertException("A new technologyClass cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TechnologyClassDTO result = technologyClassService.save(technologyClassDTO);
        return ResponseEntity.created(new URI("/api/technology-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /technology-classes : Updates an existing technologyClass.
     *
     * @param technologyClassDTO the technologyClassDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated technologyClassDTO,
     * or with status 400 (Bad Request) if the technologyClassDTO is not valid,
     * or with status 500 (Internal Server Error) if the technologyClassDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/technology-classes")
    @Timed
    public ResponseEntity<TechnologyClassDTO> updateTechnologyClass(@Valid @RequestBody TechnologyClassDTO technologyClassDTO) throws URISyntaxException {
        log.debug("REST request to update TechnologyClass : {}", technologyClassDTO);
        if (technologyClassDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TechnologyClassDTO result = technologyClassService.save(technologyClassDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, technologyClassDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /technology-classes : get all the technologyClasses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of technologyClasses in body
     */
    @GetMapping("/technology-classes")
    @Timed
    public ResponseEntity<List<TechnologyClassDTO>> getAllTechnologyClasses(Pageable pageable) {
        log.debug("REST request to get a page of TechnologyClasses");
        Page<TechnologyClassDTO> page = technologyClassService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/technology-classes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /technology-classes/:id : get the "id" technologyClass.
     *
     * @param id the id of the technologyClassDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the technologyClassDTO, or with status 404 (Not Found)
     */
    @GetMapping("/technology-classes/{id}")
    @Timed
    public ResponseEntity<TechnologyClassDTO> getTechnologyClass(@PathVariable Long id) {
        log.debug("REST request to get TechnologyClass : {}", id);
        Optional<TechnologyClassDTO> technologyClassDTO = technologyClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(technologyClassDTO);
    }

    /**
     * DELETE  /technology-classes/:id : delete the "id" technologyClass.
     *
     * @param id the id of the technologyClassDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/technology-classes/{id}")
    @Timed
    public ResponseEntity<Void> deleteTechnologyClass(@PathVariable Long id) {
        log.debug("REST request to delete TechnologyClass : {}", id);
        technologyClassService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/technology-classes?query=:query : search for the technologyClass corresponding
     * to the query.
     *
     * @param query the query of the technologyClass search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/technology-classes")
    @Timed
    public ResponseEntity<List<TechnologyClassDTO>> searchTechnologyClasses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TechnologyClasses for query {}", query);
        Page<TechnologyClassDTO> page = technologyClassService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/technology-classes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
