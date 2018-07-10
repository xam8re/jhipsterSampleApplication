package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ServiceRequestClassService;
import io.github.jhipster.application.domain.ServiceRequestClass;
import io.github.jhipster.application.repository.ServiceRequestClassRepository;
import io.github.jhipster.application.repository.search.ServiceRequestClassSearchRepository;
import io.github.jhipster.application.service.dto.ServiceRequestClassDTO;
import io.github.jhipster.application.service.mapper.ServiceRequestClassMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ServiceRequestClass.
 */
@Service
@Transactional
public class ServiceRequestClassServiceImpl implements ServiceRequestClassService {

    private final Logger log = LoggerFactory.getLogger(ServiceRequestClassServiceImpl.class);

    private final ServiceRequestClassRepository serviceRequestClassRepository;

    private final ServiceRequestClassMapper serviceRequestClassMapper;

    private final ServiceRequestClassSearchRepository serviceRequestClassSearchRepository;

    public ServiceRequestClassServiceImpl(ServiceRequestClassRepository serviceRequestClassRepository, ServiceRequestClassMapper serviceRequestClassMapper, ServiceRequestClassSearchRepository serviceRequestClassSearchRepository) {
        this.serviceRequestClassRepository = serviceRequestClassRepository;
        this.serviceRequestClassMapper = serviceRequestClassMapper;
        this.serviceRequestClassSearchRepository = serviceRequestClassSearchRepository;
    }

    /**
     * Save a serviceRequestClass.
     *
     * @param serviceRequestClassDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ServiceRequestClassDTO save(ServiceRequestClassDTO serviceRequestClassDTO) {
        log.debug("Request to save ServiceRequestClass : {}", serviceRequestClassDTO);
        ServiceRequestClass serviceRequestClass = serviceRequestClassMapper.toEntity(serviceRequestClassDTO);
        serviceRequestClass = serviceRequestClassRepository.save(serviceRequestClass);
        ServiceRequestClassDTO result = serviceRequestClassMapper.toDto(serviceRequestClass);
        serviceRequestClassSearchRepository.save(serviceRequestClass);
        return result;
    }

    /**
     * Get all the serviceRequestClasses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceRequestClassDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceRequestClasses");
        return serviceRequestClassRepository.findAll(pageable)
            .map(serviceRequestClassMapper::toDto);
    }


    /**
     * Get one serviceRequestClass by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceRequestClassDTO> findOne(Long id) {
        log.debug("Request to get ServiceRequestClass : {}", id);
        return serviceRequestClassRepository.findById(id)
            .map(serviceRequestClassMapper::toDto);
    }

    /**
     * Delete the serviceRequestClass by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceRequestClass : {}", id);
        serviceRequestClassRepository.deleteById(id);
        serviceRequestClassSearchRepository.deleteById(id);
    }

    /**
     * Search for the serviceRequestClass corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceRequestClassDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ServiceRequestClasses for query {}", query);
        return serviceRequestClassSearchRepository.search(queryStringQuery(query), pageable)
            .map(serviceRequestClassMapper::toDto);
    }
}
