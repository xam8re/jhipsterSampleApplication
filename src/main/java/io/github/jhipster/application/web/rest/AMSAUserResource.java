package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.AMSAUserService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.AMSAUserDTO;
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
 * REST controller for managing AMSAUser.
 */
@RestController
@RequestMapping("/api")
public class AMSAUserResource {

    private final Logger log = LoggerFactory.getLogger(AMSAUserResource.class);

    private static final String ENTITY_NAME = "aMSAUser";

    private final AMSAUserService aMSAUserService;

    public AMSAUserResource(AMSAUserService aMSAUserService) {
        this.aMSAUserService = aMSAUserService;
    }

    /**
     * POST  /amsa-users : Create a new aMSAUser.
     *
     * @param aMSAUserDTO the aMSAUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aMSAUserDTO, or with status 400 (Bad Request) if the aMSAUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/amsa-users")
    @Timed
    public ResponseEntity<AMSAUserDTO> createAMSAUser(@Valid @RequestBody AMSAUserDTO aMSAUserDTO) throws URISyntaxException {
        log.debug("REST request to save AMSAUser : {}", aMSAUserDTO);
        if (aMSAUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new aMSAUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AMSAUserDTO result = aMSAUserService.save(aMSAUserDTO);
        return ResponseEntity.created(new URI("/api/amsa-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /amsa-users : Updates an existing aMSAUser.
     *
     * @param aMSAUserDTO the aMSAUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aMSAUserDTO,
     * or with status 400 (Bad Request) if the aMSAUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the aMSAUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/amsa-users")
    @Timed
    public ResponseEntity<AMSAUserDTO> updateAMSAUser(@Valid @RequestBody AMSAUserDTO aMSAUserDTO) throws URISyntaxException {
        log.debug("REST request to update AMSAUser : {}", aMSAUserDTO);
        if (aMSAUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AMSAUserDTO result = aMSAUserService.save(aMSAUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aMSAUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /amsa-users : get all the aMSAUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of aMSAUsers in body
     */
    @GetMapping("/amsa-users")
    @Timed
    public ResponseEntity<List<AMSAUserDTO>> getAllAMSAUsers(Pageable pageable) {
        log.debug("REST request to get a page of AMSAUsers");
        Page<AMSAUserDTO> page = aMSAUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/amsa-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /amsa-users/:id : get the "id" aMSAUser.
     *
     * @param id the id of the aMSAUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aMSAUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/amsa-users/{id}")
    @Timed
    public ResponseEntity<AMSAUserDTO> getAMSAUser(@PathVariable Long id) {
        log.debug("REST request to get AMSAUser : {}", id);
        Optional<AMSAUserDTO> aMSAUserDTO = aMSAUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aMSAUserDTO);
    }

    /**
     * DELETE  /amsa-users/:id : delete the "id" aMSAUser.
     *
     * @param id the id of the aMSAUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/amsa-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteAMSAUser(@PathVariable Long id) {
        log.debug("REST request to delete AMSAUser : {}", id);
        aMSAUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/amsa-users?query=:query : search for the aMSAUser corresponding
     * to the query.
     *
     * @param query the query of the aMSAUser search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/amsa-users")
    @Timed
    public ResponseEntity<List<AMSAUserDTO>> searchAMSAUsers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AMSAUsers for query {}", query);
        Page<AMSAUserDTO> page = aMSAUserService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/amsa-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
