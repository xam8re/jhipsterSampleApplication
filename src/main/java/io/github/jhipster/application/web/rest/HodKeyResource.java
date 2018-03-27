package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.HodKey;
import io.github.jhipster.application.service.HodKeyService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing HodKey.
 */
@RestController
@RequestMapping("/api")
public class HodKeyResource {

    private final Logger log = LoggerFactory.getLogger(HodKeyResource.class);

    private static final String ENTITY_NAME = "hodKey";

    private final HodKeyService hodKeyService;

    public HodKeyResource(HodKeyService hodKeyService) {
        this.hodKeyService = hodKeyService;
    }

    /**
     * POST  /hod-keys : Create a new hodKey.
     *
     * @param hodKey the hodKey to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hodKey, or with status 400 (Bad Request) if the hodKey has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hod-keys")
    @Timed
    public ResponseEntity<HodKey> createHodKey(@Valid @RequestBody HodKey hodKey) throws URISyntaxException {
        log.debug("REST request to save HodKey : {}", hodKey);
        if (hodKey.getId() != null) {
            throw new BadRequestAlertException("A new hodKey cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HodKey result = hodKeyService.save(hodKey);
        return ResponseEntity.created(new URI("/api/hod-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hod-keys : Updates an existing hodKey.
     *
     * @param hodKey the hodKey to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hodKey,
     * or with status 400 (Bad Request) if the hodKey is not valid,
     * or with status 500 (Internal Server Error) if the hodKey couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hod-keys")
    @Timed
    public ResponseEntity<HodKey> updateHodKey(@Valid @RequestBody HodKey hodKey) throws URISyntaxException {
        log.debug("REST request to update HodKey : {}", hodKey);
        if (hodKey.getId() == null) {
            return createHodKey(hodKey);
        }
        HodKey result = hodKeyService.save(hodKey);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hodKey.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hod-keys : get all the hodKeys.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of hodKeys in body
     */
    @GetMapping("/hod-keys")
    @Timed
    public ResponseEntity<List<HodKey>> getAllHodKeys(Pageable pageable) {
        log.debug("REST request to get a page of HodKeys");
        Page<HodKey> page = hodKeyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hod-keys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hod-keys/:id : get the "id" hodKey.
     *
     * @param id the id of the hodKey to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hodKey, or with status 404 (Not Found)
     */
    @GetMapping("/hod-keys/{id}")
    @Timed
    public ResponseEntity<HodKey> getHodKey(@PathVariable Long id) {
        log.debug("REST request to get HodKey : {}", id);
        HodKey hodKey = hodKeyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hodKey));
    }

    /**
     * DELETE  /hod-keys/:id : delete the "id" hodKey.
     *
     * @param id the id of the hodKey to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hod-keys/{id}")
    @Timed
    public ResponseEntity<Void> deleteHodKey(@PathVariable Long id) {
        log.debug("REST request to delete HodKey : {}", id);
        hodKeyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/hod-keys?query=:query : search for the hodKey corresponding
     * to the query.
     *
     * @param query the query of the hodKey search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/hod-keys")
    @Timed
    public ResponseEntity<List<HodKey>> searchHodKeys(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of HodKeys for query {}", query);
        Page<HodKey> page = hodKeyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/hod-keys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
