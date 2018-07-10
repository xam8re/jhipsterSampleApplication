package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.MachineModelService;
import io.github.jhipster.application.domain.MachineModel;
import io.github.jhipster.application.repository.MachineModelRepository;
import io.github.jhipster.application.repository.search.MachineModelSearchRepository;
import io.github.jhipster.application.service.dto.MachineModelDTO;
import io.github.jhipster.application.service.mapper.MachineModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing MachineModel.
 */
@Service
@Transactional
public class MachineModelServiceImpl implements MachineModelService {

    private final Logger log = LoggerFactory.getLogger(MachineModelServiceImpl.class);

    private final MachineModelRepository machineModelRepository;

    private final MachineModelMapper machineModelMapper;

    private final MachineModelSearchRepository machineModelSearchRepository;

    public MachineModelServiceImpl(MachineModelRepository machineModelRepository, MachineModelMapper machineModelMapper, MachineModelSearchRepository machineModelSearchRepository) {
        this.machineModelRepository = machineModelRepository;
        this.machineModelMapper = machineModelMapper;
        this.machineModelSearchRepository = machineModelSearchRepository;
    }

    /**
     * Save a machineModel.
     *
     * @param machineModelDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MachineModelDTO save(MachineModelDTO machineModelDTO) {
        log.debug("Request to save MachineModel : {}", machineModelDTO);
        MachineModel machineModel = machineModelMapper.toEntity(machineModelDTO);
        machineModel = machineModelRepository.save(machineModel);
        MachineModelDTO result = machineModelMapper.toDto(machineModel);
        machineModelSearchRepository.save(machineModel);
        return result;
    }

    /**
     * Get all the machineModels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MachineModelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MachineModels");
        return machineModelRepository.findAll(pageable)
            .map(machineModelMapper::toDto);
    }


    /**
     * Get one machineModel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MachineModelDTO> findOne(Long id) {
        log.debug("Request to get MachineModel : {}", id);
        return machineModelRepository.findById(id)
            .map(machineModelMapper::toDto);
    }

    /**
     * Delete the machineModel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MachineModel : {}", id);
        machineModelRepository.deleteById(id);
        machineModelSearchRepository.deleteById(id);
    }

    /**
     * Search for the machineModel corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MachineModelDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MachineModels for query {}", query);
        return machineModelSearchRepository.search(queryStringQuery(query), pageable)
            .map(machineModelMapper::toDto);
    }
}
