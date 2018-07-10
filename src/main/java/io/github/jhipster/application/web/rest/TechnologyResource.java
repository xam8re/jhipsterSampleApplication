package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.TechnologyService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.TechnologyDTO;
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
 * REST controller for managing Technology.
 */
@RestController
@RequestMapping("/api")
public class TechnologyResource {

    private final Logger log = LoggerFactory.getLogger(TechnologyResource.class);

    private static final String ENTITY_NAME = "technology";

    private final TechnologyService technologyService;

    public TechnologyResource(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    /**
     * POST  /technologies : Create a new technology.
     *
     * @param technologyDTO the technologyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new technologyDTO, or with status 400 (Bad Request) if the technology has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/technologies")
    @Timed
    public ResponseEntity<TechnologyDTO> createTechnology(@Valid @RequestBody TechnologyDTO technologyDTO) throws URISyntaxException {
        log.debug("REST request to save Technology : {}", technologyDTO);
        if (technologyDTO.getId() != null) {
            throw new BadRequestAlertException("A new technology cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TechnologyDTO result = technologyService.save(technologyDTO);
        return ResponseEntity.created(new URI("/api/technologies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /technologies : Updates an existing technology.
     *
     * @param technologyDTO the technologyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated technologyDTO,
     * or with status 400 (Bad Request) if the technologyDTO is not valid,
     * or with status 500 (Internal Server Error) if the technologyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/technologies")
    @Timed
    public ResponseEntity<TechnologyDTO> updateTechnology(@Valid @RequestBody TechnologyDTO technologyDTO) throws URISyntaxException {
        log.debug("REST request to update Technology : {}", technologyDTO);
        if (technologyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TechnologyDTO result = technologyService.save(technologyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, technologyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /technologies : get all the technologies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of technologies in body
     */
    @GetMapping("/technologies")
    @Timed
    public ResponseEntity<List<TechnologyDTO>> getAllTechnologies(Pageable pageable) {
        log.debug("REST request to get a page of Technologies");
        Page<TechnologyDTO> page = technologyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/technologies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /technologies/:id : get the "id" technology.
     *
     * @param id the id of the technologyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the technologyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/technologies/{id}")
    @Timed
    public ResponseEntity<TechnologyDTO> getTechnology(@PathVariable Long id) {
        log.debug("REST request to get Technology : {}", id);
        Optional<TechnologyDTO> technologyDTO = technologyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(technologyDTO);
    }

    /**
     * DELETE  /technologies/:id : delete the "id" technology.
     *
     * @param id the id of the technologyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/technologies/{id}")
    @Timed
    public ResponseEntity<Void> deleteTechnology(@PathVariable Long id) {
        log.debug("REST request to delete Technology : {}", id);
        technologyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/technologies?query=:query : search for the technology corresponding
     * to the query.
     *
     * @param query the query of the technology search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/technologies")
    @Timed
    public ResponseEntity<List<TechnologyDTO>> searchTechnologies(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Technologies for query {}", query);
        Page<TechnologyDTO> page = technologyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/technologies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
