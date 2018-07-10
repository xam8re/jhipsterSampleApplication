package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.AMSAUserService;
import io.github.jhipster.application.domain.AMSAUser;
import io.github.jhipster.application.repository.AMSAUserRepository;
import io.github.jhipster.application.repository.search.AMSAUserSearchRepository;
import io.github.jhipster.application.service.dto.AMSAUserDTO;
import io.github.jhipster.application.service.mapper.AMSAUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AMSAUser.
 */
@Service
@Transactional
public class AMSAUserServiceImpl implements AMSAUserService {

    private final Logger log = LoggerFactory.getLogger(AMSAUserServiceImpl.class);

    private final AMSAUserRepository aMSAUserRepository;

    private final AMSAUserMapper aMSAUserMapper;

    private final AMSAUserSearchRepository aMSAUserSearchRepository;

    public AMSAUserServiceImpl(AMSAUserRepository aMSAUserRepository, AMSAUserMapper aMSAUserMapper, AMSAUserSearchRepository aMSAUserSearchRepository) {
        this.aMSAUserRepository = aMSAUserRepository;
        this.aMSAUserMapper = aMSAUserMapper;
        this.aMSAUserSearchRepository = aMSAUserSearchRepository;
    }

    /**
     * Save a aMSAUser.
     *
     * @param aMSAUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AMSAUserDTO save(AMSAUserDTO aMSAUserDTO) {
        log.debug("Request to save AMSAUser : {}", aMSAUserDTO);
        AMSAUser aMSAUser = aMSAUserMapper.toEntity(aMSAUserDTO);
        aMSAUser = aMSAUserRepository.save(aMSAUser);
        AMSAUserDTO result = aMSAUserMapper.toDto(aMSAUser);
        aMSAUserSearchRepository.save(aMSAUser);
        return result;
    }

    /**
     * Get all the aMSAUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AMSAUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AMSAUsers");
        return aMSAUserRepository.findAll(pageable)
            .map(aMSAUserMapper::toDto);
    }


    /**
     * Get one aMSAUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AMSAUserDTO> findOne(Long id) {
        log.debug("Request to get AMSAUser : {}", id);
        return aMSAUserRepository.findById(id)
            .map(aMSAUserMapper::toDto);
    }

    /**
     * Delete the aMSAUser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AMSAUser : {}", id);
        aMSAUserRepository.deleteById(id);
        aMSAUserSearchRepository.deleteById(id);
    }

    /**
     * Search for the aMSAUser corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AMSAUserDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AMSAUsers for query {}", query);
        return aMSAUserSearchRepository.search(queryStringQuery(query), pageable)
            .map(aMSAUserMapper::toDto);
    }
}
