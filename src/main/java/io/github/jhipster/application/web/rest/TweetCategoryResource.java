package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.TweetCategory;
import io.github.jhipster.application.service.TweetCategoryService;
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
 * REST controller for managing TweetCategory.
 */
@RestController
@RequestMapping("/api")
public class TweetCategoryResource {

    private final Logger log = LoggerFactory.getLogger(TweetCategoryResource.class);

    private static final String ENTITY_NAME = "tweetCategory";

    private final TweetCategoryService tweetCategoryService;

    public TweetCategoryResource(TweetCategoryService tweetCategoryService) {
        this.tweetCategoryService = tweetCategoryService;
    }

    /**
     * POST  /tweet-categories : Create a new tweetCategory.
     *
     * @param tweetCategory the tweetCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tweetCategory, or with status 400 (Bad Request) if the tweetCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tweet-categories")
    @Timed
    public ResponseEntity<TweetCategory> createTweetCategory(@Valid @RequestBody TweetCategory tweetCategory) throws URISyntaxException {
        log.debug("REST request to save TweetCategory : {}", tweetCategory);
        if (tweetCategory.getId() != null) {
            throw new BadRequestAlertException("A new tweetCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TweetCategory result = tweetCategoryService.save(tweetCategory);
        return ResponseEntity.created(new URI("/api/tweet-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tweet-categories : Updates an existing tweetCategory.
     *
     * @param tweetCategory the tweetCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tweetCategory,
     * or with status 400 (Bad Request) if the tweetCategory is not valid,
     * or with status 500 (Internal Server Error) if the tweetCategory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tweet-categories")
    @Timed
    public ResponseEntity<TweetCategory> updateTweetCategory(@Valid @RequestBody TweetCategory tweetCategory) throws URISyntaxException {
        log.debug("REST request to update TweetCategory : {}", tweetCategory);
        if (tweetCategory.getId() == null) {
            return createTweetCategory(tweetCategory);
        }
        TweetCategory result = tweetCategoryService.save(tweetCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tweetCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tweet-categories : get all the tweetCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tweetCategories in body
     */
    @GetMapping("/tweet-categories")
    @Timed
    public ResponseEntity<List<TweetCategory>> getAllTweetCategories(Pageable pageable) {
        log.debug("REST request to get a page of TweetCategories");
        Page<TweetCategory> page = tweetCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tweet-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tweet-categories/:id : get the "id" tweetCategory.
     *
     * @param id the id of the tweetCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tweetCategory, or with status 404 (Not Found)
     */
    @GetMapping("/tweet-categories/{id}")
    @Timed
    public ResponseEntity<TweetCategory> getTweetCategory(@PathVariable Long id) {
        log.debug("REST request to get TweetCategory : {}", id);
        TweetCategory tweetCategory = tweetCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tweetCategory));
    }

    /**
     * DELETE  /tweet-categories/:id : delete the "id" tweetCategory.
     *
     * @param id the id of the tweetCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tweet-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteTweetCategory(@PathVariable Long id) {
        log.debug("REST request to delete TweetCategory : {}", id);
        tweetCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tweet-categories?query=:query : search for the tweetCategory corresponding
     * to the query.
     *
     * @param query the query of the tweetCategory search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/tweet-categories")
    @Timed
    public ResponseEntity<List<TweetCategory>> searchTweetCategories(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TweetCategories for query {}", query);
        Page<TweetCategory> page = tweetCategoryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tweet-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
