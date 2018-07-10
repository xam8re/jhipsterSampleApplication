package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ServiceOrderService;
import io.github.jhipster.application.domain.ServiceOrder;
import io.github.jhipster.application.repository.ServiceOrderRepository;
import io.github.jhipster.application.repository.search.ServiceOrderSearchRepository;
import io.github.jhipster.application.service.dto.ServiceOrderDTO;
import io.github.jhipster.application.service.mapper.ServiceOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ServiceOrder.
 */
@Service
@Transactional
public class ServiceOrderServiceImpl implements ServiceOrderService {

    private final Logger log = LoggerFactory.getLogger(ServiceOrderServiceImpl.class);

    private final ServiceOrderRepository serviceOrderRepository;

    private final ServiceOrderMapper serviceOrderMapper;

    private final ServiceOrderSearchRepository serviceOrderSearchRepository;

    public ServiceOrderServiceImpl(ServiceOrderRepository serviceOrderRepository, ServiceOrderMapper serviceOrderMapper, ServiceOrderSearchRepository serviceOrderSearchRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.serviceOrderMapper = serviceOrderMapper;
        this.serviceOrderSearchRepository = serviceOrderSearchRepository;
    }

    /**
     * Save a serviceOrder.
     *
     * @param serviceOrderDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ServiceOrderDTO save(ServiceOrderDTO serviceOrderDTO) {
        log.debug("Request to save ServiceOrder : {}", serviceOrderDTO);
        ServiceOrder serviceOrder = serviceOrderMapper.toEntity(serviceOrderDTO);
        serviceOrder = serviceOrderRepository.save(serviceOrder);
        ServiceOrderDTO result = serviceOrderMapper.toDto(serviceOrder);
        serviceOrderSearchRepository.save(serviceOrder);
        return result;
    }

    /**
     * Get all the serviceOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceOrders");
        return serviceOrderRepository.findAll(pageable)
            .map(serviceOrderMapper::toDto);
    }


    /**
     * Get one serviceOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceOrderDTO> findOne(Long id) {
        log.debug("Request to get ServiceOrder : {}", id);
        return serviceOrderRepository.findById(id)
            .map(serviceOrderMapper::toDto);
    }

    /**
     * Delete the serviceOrder by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceOrder : {}", id);
        serviceOrderRepository.deleteById(id);
        serviceOrderSearchRepository.deleteById(id);
    }

    /**
     * Search for the serviceOrder corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceOrderDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ServiceOrders for query {}", query);
        return serviceOrderSearchRepository.search(queryStringQuery(query), pageable)
            .map(serviceOrderMapper::toDto);
    }
}
