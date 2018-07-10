package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.ServiceOfferService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.ServiceOfferDTO;
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
 * REST controller for managing ServiceOffer.
 */
@RestController
@RequestMapping("/api")
public class ServiceOfferResource {

    private final Logger log = LoggerFactory.getLogger(ServiceOfferResource.class);

    private static final String ENTITY_NAME = "serviceOffer";

    private final ServiceOfferService serviceOfferService;

    public ServiceOfferResource(ServiceOfferService serviceOfferService) {
        this.serviceOfferService = serviceOfferService;
    }

    /**
     * POST  /service-offers : Create a new serviceOffer.
     *
     * @param serviceOfferDTO the serviceOfferDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviceOfferDTO, or with status 400 (Bad Request) if the serviceOffer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/service-offers")
    @Timed
    public ResponseEntity<ServiceOfferDTO> createServiceOffer(@Valid @RequestBody ServiceOfferDTO serviceOfferDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceOffer : {}", serviceOfferDTO);
        if (serviceOfferDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceOffer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceOfferDTO result = serviceOfferService.save(serviceOfferDTO);
        return ResponseEntity.created(new URI("/api/service-offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /service-offers : Updates an existing serviceOffer.
     *
     * @param serviceOfferDTO the serviceOfferDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceOfferDTO,
     * or with status 400 (Bad Request) if the serviceOfferDTO is not valid,
     * or with status 500 (Internal Server Error) if the serviceOfferDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/service-offers")
    @Timed
    public ResponseEntity<ServiceOfferDTO> updateServiceOffer(@Valid @RequestBody ServiceOfferDTO serviceOfferDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceOffer : {}", serviceOfferDTO);
        if (serviceOfferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceOfferDTO result = serviceOfferService.save(serviceOfferDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serviceOfferDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /service-offers : get all the serviceOffers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of serviceOffers in body
     */
    @GetMapping("/service-offers")
    @Timed
    public ResponseEntity<List<ServiceOfferDTO>> getAllServiceOffers(Pageable pageable) {
        log.debug("REST request to get a page of ServiceOffers");
        Page<ServiceOfferDTO> page = serviceOfferService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/service-offers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /service-offers/:id : get the "id" serviceOffer.
     *
     * @param id the id of the serviceOfferDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviceOfferDTO, or with status 404 (Not Found)
     */
    @GetMapping("/service-offers/{id}")
    @Timed
    public ResponseEntity<ServiceOfferDTO> getServiceOffer(@PathVariable Long id) {
        log.debug("REST request to get ServiceOffer : {}", id);
        Optional<ServiceOfferDTO> serviceOfferDTO = serviceOfferService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceOfferDTO);
    }

    /**
     * DELETE  /service-offers/:id : delete the "id" serviceOffer.
     *
     * @param id the id of the serviceOfferDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/service-offers/{id}")
    @Timed
    public ResponseEntity<Void> deleteServiceOffer(@PathVariable Long id) {
        log.debug("REST request to delete ServiceOffer : {}", id);
        serviceOfferService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/service-offers?query=:query : search for the serviceOffer corresponding
     * to the query.
     *
     * @param query the query of the serviceOffer search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/service-offers")
    @Timed
    public ResponseEntity<List<ServiceOfferDTO>> searchServiceOffers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ServiceOffers for query {}", query);
        Page<ServiceOfferDTO> page = serviceOfferService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/service-offers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
