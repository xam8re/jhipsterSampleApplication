package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ServiceRequestService;
import io.github.jhipster.application.domain.ServiceRequest;
import io.github.jhipster.application.repository.ServiceRequestRepository;
import io.github.jhipster.application.repository.search.ServiceRequestSearchRepository;
import io.github.jhipster.application.service.dto.ServiceRequestDTO;
import io.github.jhipster.application.service.mapper.ServiceRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ServiceRequest.
 */
@Service
@Transactional
public class ServiceRequestServiceImpl implements ServiceRequestService {

    private final Logger log = LoggerFactory.getLogger(ServiceRequestServiceImpl.class);

    private final ServiceRequestRepository serviceRequestRepository;

    private final ServiceRequestMapper serviceRequestMapper;

    private final ServiceRequestSearchRepository serviceRequestSearchRepository;

    public ServiceRequestServiceImpl(ServiceRequestRepository serviceRequestRepository, ServiceRequestMapper serviceRequestMapper, ServiceRequestSearchRepository serviceRequestSearchRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.serviceRequestMapper = serviceRequestMapper;
        this.serviceRequestSearchRepository = serviceRequestSearchRepository;
    }

    /**
     * Save a serviceRequest.
     *
     * @param serviceRequestDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ServiceRequestDTO save(ServiceRequestDTO serviceRequestDTO) {
        log.debug("Request to save ServiceRequest : {}", serviceRequestDTO);
        ServiceRequest serviceRequest = serviceRequestMapper.toEntity(serviceRequestDTO);
        serviceRequest = serviceRequestRepository.save(serviceRequest);
        ServiceRequestDTO result = serviceRequestMapper.toDto(serviceRequest);
        serviceRequestSearchRepository.save(serviceRequest);
        return result;
    }

    /**
     * Get all the serviceRequests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceRequests");
        return serviceRequestRepository.findAll(pageable)
            .map(serviceRequestMapper::toDto);
    }

    /**
     * Get all the ServiceRequest with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ServiceRequestDTO> findAllWithEagerRelationships(Pageable pageable) {
        return serviceRequestRepository.findAllWithEagerRelationships(pageable).map(serviceRequestMapper::toDto);
    }
    

    /**
     * Get one serviceRequest by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceRequestDTO> findOne(Long id) {
        log.debug("Request to get ServiceRequest : {}", id);
        return serviceRequestRepository.findOneWithEagerRelationships(id)
            .map(serviceRequestMapper::toDto);
    }

    /**
     * Delete the serviceRequest by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceRequest : {}", id);
        serviceRequestRepository.deleteById(id);
        serviceRequestSearchRepository.deleteById(id);
    }

    /**
     * Search for the serviceRequest corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceRequestDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ServiceRequests for query {}", query);
        return serviceRequestSearchRepository.search(queryStringQuery(query), pageable)
            .map(serviceRequestMapper::toDto);
    }
}
