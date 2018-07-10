package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.service.MachineModelService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.MachineModelDTO;
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
 * REST controller for managing MachineModel.
 */
@RestController
@RequestMapping("/api")
public class MachineModelResource {

    private final Logger log = LoggerFactory.getLogger(MachineModelResource.class);

    private static final String ENTITY_NAME = "machineModel";

    private final MachineModelService machineModelService;

    public MachineModelResource(MachineModelService machineModelService) {
        this.machineModelService = machineModelService;
    }

    /**
     * POST  /machine-models : Create a new machineModel.
     *
     * @param machineModelDTO the machineModelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new machineModelDTO, or with status 400 (Bad Request) if the machineModel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/machine-models")
    @Timed
    public ResponseEntity<MachineModelDTO> createMachineModel(@Valid @RequestBody MachineModelDTO machineModelDTO) throws URISyntaxException {
        log.debug("REST request to save MachineModel : {}", machineModelDTO);
        if (machineModelDTO.getId() != null) {
            throw new BadRequestAlertException("A new machineModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MachineModelDTO result = machineModelService.save(machineModelDTO);
        return ResponseEntity.created(new URI("/api/machine-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /machine-models : Updates an existing machineModel.
     *
     * @param machineModelDTO the machineModelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated machineModelDTO,
     * or with status 400 (Bad Request) if the machineModelDTO is not valid,
     * or with status 500 (Internal Server Error) if the machineModelDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/machine-models")
    @Timed
    public ResponseEntity<MachineModelDTO> updateMachineModel(@Valid @RequestBody MachineModelDTO machineModelDTO) throws URISyntaxException {
        log.debug("REST request to update MachineModel : {}", machineModelDTO);
        if (machineModelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MachineModelDTO result = machineModelService.save(machineModelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, machineModelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /machine-models : get all the machineModels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of machineModels in body
     */
    @GetMapping("/machine-models")
    @Timed
    public ResponseEntity<List<MachineModelDTO>> getAllMachineModels(Pageable pageable) {
        log.debug("REST request to get a page of MachineModels");
        Page<MachineModelDTO> page = machineModelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/machine-models");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /machine-models/:id : get the "id" machineModel.
     *
     * @param id the id of the machineModelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the machineModelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/machine-models/{id}")
    @Timed
    public ResponseEntity<MachineModelDTO> getMachineModel(@PathVariable Long id) {
        log.debug("REST request to get MachineModel : {}", id);
        Optional<MachineModelDTO> machineModelDTO = machineModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(machineModelDTO);
    }

    /**
     * DELETE  /machine-models/:id : delete the "id" machineModel.
     *
     * @param id the id of the machineModelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/machine-models/{id}")
    @Timed
    public ResponseEntity<Void> deleteMachineModel(@PathVariable Long id) {
        log.debug("REST request to delete MachineModel : {}", id);
        machineModelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/machine-models?query=:query : search for the machineModel corresponding
     * to the query.
     *
     * @param query the query of the machineModel search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/machine-models")
    @Timed
    public ResponseEntity<List<MachineModelDTO>> searchMachineModels(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MachineModels for query {}", query);
        Page<MachineModelDTO> page = machineModelService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/machine-models");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
