package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.TaskOfferService;
import io.github.jhipster.application.domain.TaskOffer;
import io.github.jhipster.application.repository.TaskOfferRepository;
import io.github.jhipster.application.repository.search.TaskOfferSearchRepository;
import io.github.jhipster.application.service.dto.TaskOfferDTO;
import io.github.jhipster.application.service.mapper.TaskOfferMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TaskOffer.
 */
@Service
@Transactional
public class TaskOfferServiceImpl implements TaskOfferService {

    private final Logger log = LoggerFactory.getLogger(TaskOfferServiceImpl.class);

    private final TaskOfferRepository taskOfferRepository;

    private final TaskOfferMapper taskOfferMapper;

    private final TaskOfferSearchRepository taskOfferSearchRepository;

    public TaskOfferServiceImpl(TaskOfferRepository taskOfferRepository, TaskOfferMapper taskOfferMapper, TaskOfferSearchRepository taskOfferSearchRepository) {
        this.taskOfferRepository = taskOfferRepository;
        this.taskOfferMapper = taskOfferMapper;
        this.taskOfferSearchRepository = taskOfferSearchRepository;
    }

    /**
     * Save a taskOffer.
     *
     * @param taskOfferDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TaskOfferDTO save(TaskOfferDTO taskOfferDTO) {
        log.debug("Request to save TaskOffer : {}", taskOfferDTO);
        TaskOffer taskOffer = taskOfferMapper.toEntity(taskOfferDTO);
        taskOffer = taskOfferRepository.save(taskOffer);
        TaskOfferDTO result = taskOfferMapper.toDto(taskOffer);
        taskOfferSearchRepository.save(taskOffer);
        return result;
    }

    /**
     * Get all the taskOffers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TaskOfferDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskOffers");
        return taskOfferRepository.findAll(pageable)
            .map(taskOfferMapper::toDto);
    }


    /**
     * Get one taskOffer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TaskOfferDTO> findOne(Long id) {
        log.debug("Request to get TaskOffer : {}", id);
        return taskOfferRepository.findById(id)
            .map(taskOfferMapper::toDto);
    }

    /**
     * Delete the taskOffer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaskOffer : {}", id);
        taskOfferRepository.deleteById(id);
        taskOfferSearchRepository.deleteById(id);
    }

    /**
     * Search for the taskOffer corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TaskOfferDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TaskOffers for query {}", query);
        return taskOfferSearchRepository.search(queryStringQuery(query), pageable)
            .map(taskOfferMapper::toDto);
    }
}
