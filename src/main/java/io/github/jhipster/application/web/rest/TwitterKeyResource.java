package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.TwitterKey;
import io.github.jhipster.application.service.TwitterKeyService;
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
 * REST controller for managing TwitterKey.
 */
@RestController
@RequestMapping("/api")
public class TwitterKeyResource {

    private final Logger log = LoggerFactory.getLogger(TwitterKeyResource.class);

    private static final String ENTITY_NAME = "twitterKey";

    private final TwitterKeyService twitterKeyService;

    public TwitterKeyResource(TwitterKeyService twitterKeyService) {
        this.twitterKeyService = twitterKeyService;
    }

    /**
     * POST  /twitter-keys : Create a new twitterKey.
     *
     * @param twitterKey the twitterKey to create
     * @return the ResponseEntity with status 201 (Created) and with body the new twitterKey, or with status 400 (Bad Request) if the twitterKey has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/twitter-keys")
    @Timed
    public ResponseEntity<TwitterKey> createTwitterKey(@Valid @RequestBody TwitterKey twitterKey) throws URISyntaxException {
        log.debug("REST request to save TwitterKey : {}", twitterKey);
        if (twitterKey.getId() != null) {
            throw new BadRequestAlertException("A new twitterKey cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TwitterKey result = twitterKeyService.save(twitterKey);
        return ResponseEntity.created(new URI("/api/twitter-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /twitter-keys : Updates an existing twitterKey.
     *
     * @param twitterKey the twitterKey to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated twitterKey,
     * or with status 400 (Bad Request) if the twitterKey is not valid,
     * or with status 500 (Internal Server Error) if the twitterKey couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/twitter-keys")
    @Timed
    public ResponseEntity<TwitterKey> updateTwitterKey(@Valid @RequestBody TwitterKey twitterKey) throws URISyntaxException {
        log.debug("REST request to update TwitterKey : {}", twitterKey);
        if (twitterKey.getId() == null) {
            return createTwitterKey(twitterKey);
        }
        TwitterKey result = twitterKeyService.save(twitterKey);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, twitterKey.getId().toString()))
            .body(result);
    }

    /**
     * GET  /twitter-keys : get all the twitterKeys.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of twitterKeys in body
     */
    @GetMapping("/twitter-keys")
    @Timed
    public ResponseEntity<List<TwitterKey>> getAllTwitterKeys(Pageable pageable) {
        log.debug("REST request to get a page of TwitterKeys");
        Page<TwitterKey> page = twitterKeyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/twitter-keys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /twitter-keys/:id : get the "id" twitterKey.
     *
     * @param id the id of the twitterKey to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the twitterKey, or with status 404 (Not Found)
     */
    @GetMapping("/twitter-keys/{id}")
    @Timed
    public ResponseEntity<TwitterKey> getTwitterKey(@PathVariable Long id) {
        log.debug("REST request to get TwitterKey : {}", id);
        TwitterKey twitterKey = twitterKeyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(twitterKey));
    }

    /**
     * DELETE  /twitter-keys/:id : delete the "id" twitterKey.
     *
     * @param id the id of the twitterKey to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/twitter-keys/{id}")
    @Timed
    public ResponseEntity<Void> deleteTwitterKey(@PathVariable Long id) {
        log.debug("REST request to delete TwitterKey : {}", id);
        twitterKeyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/twitter-keys?query=:query : search for the twitterKey corresponding
     * to the query.
     *
     * @param query the query of the twitterKey search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/twitter-keys")
    @Timed
    public ResponseEntity<List<TwitterKey>> searchTwitterKeys(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TwitterKeys for query {}", query);
        Page<TwitterKey> page = twitterKeyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/twitter-keys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
