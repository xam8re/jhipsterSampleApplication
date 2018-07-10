package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.ServiceRequestClassService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.ServiceRequestClassDTO;
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
 * REST controller for managing ServiceRequestClass.
 */
@RestController
@RequestMapping("/api")
public class ServiceRequestClassResource {

    private final Logger log = LoggerFactory.getLogger(ServiceRequestClassResource.class);

    private static final String ENTITY_NAME = "serviceRequestClass";

    private final ServiceRequestClassService serviceRequestClassService;

    public ServiceRequestClassResource(ServiceRequestClassService serviceRequestClassService) {
        this.serviceRequestClassService = serviceRequestClassService;
    }

    /**
     * POST  /service-request-classes : Create a new serviceRequestClass.
     *
     * @param serviceRequestClassDTO the serviceRequestClassDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviceRequestClassDTO, or with status 400 (Bad Request) if the serviceRequestClass has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/service-request-classes")
    @Timed
    public ResponseEntity<ServiceRequestClassDTO> createServiceRequestClass(@RequestBody ServiceRequestClassDTO serviceRequestClassDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceRequestClass : {}", serviceRequestClassDTO);
        if (serviceRequestClassDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceRequestClass cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceRequestClassDTO result = serviceRequestClassService.save(serviceRequestClassDTO);
        return ResponseEntity.created(new URI("/api/service-request-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /service-request-classes : Updates an existing serviceRequestClass.
     *
     * @param serviceRequestClassDTO the serviceRequestClassDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceRequestClassDTO,
     * or with status 400 (Bad Request) if the serviceRequestClassDTO is not valid,
     * or with status 500 (Internal Server Error) if the serviceRequestClassDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/service-request-classes")
    @Timed
    public ResponseEntity<ServiceRequestClassDTO> updateServiceRequestClass(@RequestBody ServiceRequestClassDTO serviceRequestClassDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceRequestClass : {}", serviceRequestClassDTO);
        if (serviceRequestClassDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceRequestClassDTO result = serviceRequestClassService.save(serviceRequestClassDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serviceRequestClassDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /service-request-classes : get all the serviceRequestClasses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of serviceRequestClasses in body
     */
    @GetMapping("/service-request-classes")
    @Timed
    public ResponseEntity<List<ServiceRequestClassDTO>> getAllServiceRequestClasses(Pageable pageable) {
        log.debug("REST request to get a page of ServiceRequestClasses");
        Page<ServiceRequestClassDTO> page = serviceRequestClassService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/service-request-classes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /service-request-classes/:id : get the "id" serviceRequestClass.
     *
     * @param id the id of the serviceRequestClassDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviceRequestClassDTO, or with status 404 (Not Found)
     */
    @GetMapping("/service-request-classes/{id}")
    @Timed
    public ResponseEntity<ServiceRequestClassDTO> getServiceRequestClass(@PathVariable Long id) {
        log.debug("REST request to get ServiceRequestClass : {}", id);
        Optional<ServiceRequestClassDTO> serviceRequestClassDTO = serviceRequestClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceRequestClassDTO);
    }

    /**
     * DELETE  /service-request-classes/:id : delete the "id" serviceRequestClass.
     *
     * @param id the id of the serviceRequestClassDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/service-request-classes/{id}")
    @Timed
    public ResponseEntity<Void> deleteServiceRequestClass(@PathVariable Long id) {
        log.debug("REST request to delete ServiceRequestClass : {}", id);
        serviceRequestClassService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/service-request-classes?query=:query : search for the serviceRequestClass corresponding
     * to the query.
     *
     * @param query the query of the serviceRequestClass search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/service-request-classes")
    @Timed
    public ResponseEntity<List<ServiceRequestClassDTO>> searchServiceRequestClasses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ServiceRequestClasses for query {}", query);
        Page<ServiceRequestClassDTO> page = serviceRequestClassService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/service-request-classes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
