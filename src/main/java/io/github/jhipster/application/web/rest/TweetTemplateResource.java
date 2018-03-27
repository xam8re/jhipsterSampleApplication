package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.TweetTemplate;
import io.github.jhipster.application.service.TweetTemplateService;
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
 * REST controller for managing TweetTemplate.
 */
@RestController
@RequestMapping("/api")
public class TweetTemplateResource {

    private final Logger log = LoggerFactory.getLogger(TweetTemplateResource.class);

    private static final String ENTITY_NAME = "tweetTemplate";

    private final TweetTemplateService tweetTemplateService;

    public TweetTemplateResource(TweetTemplateService tweetTemplateService) {
        this.tweetTemplateService = tweetTemplateService;
    }

    /**
     * POST  /tweet-templates : Create a new tweetTemplate.
     *
     * @param tweetTemplate the tweetTemplate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tweetTemplate, or with status 400 (Bad Request) if the tweetTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tweet-templates")
    @Timed
    public ResponseEntity<TweetTemplate> createTweetTemplate(@Valid @RequestBody TweetTemplate tweetTemplate) throws URISyntaxException {
        log.debug("REST request to save TweetTemplate : {}", tweetTemplate);
        if (tweetTemplate.getId() != null) {
            throw new BadRequestAlertException("A new tweetTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TweetTemplate result = tweetTemplateService.save(tweetTemplate);
        return ResponseEntity.created(new URI("/api/tweet-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tweet-templates : Updates an existing tweetTemplate.
     *
     * @param tweetTemplate the tweetTemplate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tweetTemplate,
     * or with status 400 (Bad Request) if the tweetTemplate is not valid,
     * or with status 500 (Internal Server Error) if the tweetTemplate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tweet-templates")
    @Timed
    public ResponseEntity<TweetTemplate> updateTweetTemplate(@Valid @RequestBody TweetTemplate tweetTemplate) throws URISyntaxException {
        log.debug("REST request to update TweetTemplate : {}", tweetTemplate);
        if (tweetTemplate.getId() == null) {
            return createTweetTemplate(tweetTemplate);
        }
        TweetTemplate result = tweetTemplateService.save(tweetTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tweetTemplate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tweet-templates : get all the tweetTemplates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tweetTemplates in body
     */
    @GetMapping("/tweet-templates")
    @Timed
    public ResponseEntity<List<TweetTemplate>> getAllTweetTemplates(Pageable pageable) {
        log.debug("REST request to get a page of TweetTemplates");
        Page<TweetTemplate> page = tweetTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tweet-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tweet-templates/:id : get the "id" tweetTemplate.
     *
     * @param id the id of the tweetTemplate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tweetTemplate, or with status 404 (Not Found)
     */
    @GetMapping("/tweet-templates/{id}")
    @Timed
    public ResponseEntity<TweetTemplate> getTweetTemplate(@PathVariable Long id) {
        log.debug("REST request to get TweetTemplate : {}", id);
        TweetTemplate tweetTemplate = tweetTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tweetTemplate));
    }

    /**
     * DELETE  /tweet-templates/:id : delete the "id" tweetTemplate.
     *
     * @param id the id of the tweetTemplate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tweet-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteTweetTemplate(@PathVariable Long id) {
        log.debug("REST request to delete TweetTemplate : {}", id);
        tweetTemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tweet-templates?query=:query : search for the tweetTemplate corresponding
     * to the query.
     *
     * @param query the query of the tweetTemplate search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/tweet-templates")
    @Timed
    public ResponseEntity<List<TweetTemplate>> searchTweetTemplates(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TweetTemplates for query {}", query);
        Page<TweetTemplate> page = tweetTemplateService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tweet-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
