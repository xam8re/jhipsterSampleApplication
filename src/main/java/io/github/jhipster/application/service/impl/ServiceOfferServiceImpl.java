package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ServiceOfferService;
import io.github.jhipster.application.domain.ServiceOffer;
import io.github.jhipster.application.repository.ServiceOfferRepository;
import io.github.jhipster.application.repository.search.ServiceOfferSearchRepository;
import io.github.jhipster.application.service.dto.ServiceOfferDTO;
import io.github.jhipster.application.service.mapper.ServiceOfferMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ServiceOffer.
 */
@Service
@Transactional
public class ServiceOfferServiceImpl implements ServiceOfferService {

    private final Logger log = LoggerFactory.getLogger(ServiceOfferServiceImpl.class);

    private final ServiceOfferRepository serviceOfferRepository;

    private final ServiceOfferMapper serviceOfferMapper;

    private final ServiceOfferSearchRepository serviceOfferSearchRepository;

    public ServiceOfferServiceImpl(ServiceOfferRepository serviceOfferRepository, ServiceOfferMapper serviceOfferMapper, ServiceOfferSearchRepository serviceOfferSearchRepository) {
        this.serviceOfferRepository = serviceOfferRepository;
        this.serviceOfferMapper = serviceOfferMapper;
        this.serviceOfferSearchRepository = serviceOfferSearchRepository;
    }

    /**
     * Save a serviceOffer.
     *
     * @param serviceOfferDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ServiceOfferDTO save(ServiceOfferDTO serviceOfferDTO) {
        log.debug("Request to save ServiceOffer : {}", serviceOfferDTO);
        ServiceOffer serviceOffer = serviceOfferMapper.toEntity(serviceOfferDTO);
        serviceOffer = serviceOfferRepository.save(serviceOffer);
        ServiceOfferDTO result = serviceOfferMapper.toDto(serviceOffer);
        serviceOfferSearchRepository.save(serviceOffer);
        return result;
    }

    /**
     * Get all the serviceOffers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceOfferDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceOffers");
        return serviceOfferRepository.findAll(pageable)
            .map(serviceOfferMapper::toDto);
    }


    /**
     * Get one serviceOffer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceOfferDTO> findOne(Long id) {
        log.debug("Request to get ServiceOffer : {}", id);
        return serviceOfferRepository.findById(id)
            .map(serviceOfferMapper::toDto);
    }

    /**
     * Delete the serviceOffer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceOffer : {}", id);
        serviceOfferRepository.deleteById(id);
        serviceOfferSearchRepository.deleteById(id);
    }

    /**
     * Search for the serviceOffer corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceOfferDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ServiceOffers for query {}", query);
        return serviceOfferSearchRepository.search(queryStringQuery(query), pageable)
            .map(serviceOfferMapper::toDto);
    }
}
