package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.ServiceOrderService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.ServiceOrderDTO;
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
 * REST controller for managing ServiceOrder.
 */
@RestController
@RequestMapping("/api")
public class ServiceOrderResource {

    private final Logger log = LoggerFactory.getLogger(ServiceOrderResource.class);

    private static final String ENTITY_NAME = "serviceOrder";

    private final ServiceOrderService serviceOrderService;

    public ServiceOrderResource(ServiceOrderService serviceOrderService) {
        this.serviceOrderService = serviceOrderService;
    }

    /**
     * POST  /service-orders : Create a new serviceOrder.
     *
     * @param serviceOrderDTO the serviceOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviceOrderDTO, or with status 400 (Bad Request) if the serviceOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/service-orders")
    @Timed
    public ResponseEntity<ServiceOrderDTO> createServiceOrder(@RequestBody ServiceOrderDTO serviceOrderDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceOrder : {}", serviceOrderDTO);
        if (serviceOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceOrderDTO result = serviceOrderService.save(serviceOrderDTO);
        return ResponseEntity.created(new URI("/api/service-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /service-orders : Updates an existing serviceOrder.
     *
     * @param serviceOrderDTO the serviceOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceOrderDTO,
     * or with status 400 (Bad Request) if the serviceOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the serviceOrderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/service-orders")
    @Timed
    public ResponseEntity<ServiceOrderDTO> updateServiceOrder(@RequestBody ServiceOrderDTO serviceOrderDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceOrder : {}", serviceOrderDTO);
        if (serviceOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceOrderDTO result = serviceOrderService.save(serviceOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serviceOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /service-orders : get all the serviceOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of serviceOrders in body
     */
    @GetMapping("/service-orders")
    @Timed
    public ResponseEntity<List<ServiceOrderDTO>> getAllServiceOrders(Pageable pageable) {
        log.debug("REST request to get a page of ServiceOrders");
        Page<ServiceOrderDTO> page = serviceOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/service-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /service-orders/:id : get the "id" serviceOrder.
     *
     * @param id the id of the serviceOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviceOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/service-orders/{id}")
    @Timed
    public ResponseEntity<ServiceOrderDTO> getServiceOrder(@PathVariable Long id) {
        log.debug("REST request to get ServiceOrder : {}", id);
        Optional<ServiceOrderDTO> serviceOrderDTO = serviceOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceOrderDTO);
    }

    /**
     * DELETE  /service-orders/:id : delete the "id" serviceOrder.
     *
     * @param id the id of the serviceOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/service-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteServiceOrder(@PathVariable Long id) {
        log.debug("REST request to delete ServiceOrder : {}", id);
        serviceOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/service-orders?query=:query : search for the serviceOrder corresponding
     * to the query.
     *
     * @param query the query of the serviceOrder search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/service-orders")
    @Timed
    public ResponseEntity<List<ServiceOrderDTO>> searchServiceOrders(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ServiceOrders for query {}", query);
        Page<ServiceOrderDTO> page = serviceOrderService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/service-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
