package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.OrderHistoryService;
import io.github.jhipster.application.domain.OrderHistory;
import io.github.jhipster.application.repository.OrderHistoryRepository;
import io.github.jhipster.application.repository.search.OrderHistorySearchRepository;
import io.github.jhipster.application.service.dto.OrderHistoryDTO;
import io.github.jhipster.application.service.mapper.OrderHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OrderHistory.
 */
@Service
@Transactional
public class OrderHistoryServiceImpl implements OrderHistoryService {

    private final Logger log = LoggerFactory.getLogger(OrderHistoryServiceImpl.class);

    private final OrderHistoryRepository orderHistoryRepository;

    private final OrderHistoryMapper orderHistoryMapper;

    private final OrderHistorySearchRepository orderHistorySearchRepository;

    public OrderHistoryServiceImpl(OrderHistoryRepository orderHistoryRepository, OrderHistoryMapper orderHistoryMapper, OrderHistorySearchRepository orderHistorySearchRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
        this.orderHistoryMapper = orderHistoryMapper;
        this.orderHistorySearchRepository = orderHistorySearchRepository;
    }

    /**
     * Save a orderHistory.
     *
     * @param orderHistoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrderHistoryDTO save(OrderHistoryDTO orderHistoryDTO) {
        log.debug("Request to save OrderHistory : {}", orderHistoryDTO);
        OrderHistory orderHistory = orderHistoryMapper.toEntity(orderHistoryDTO);
        orderHistory = orderHistoryRepository.save(orderHistory);
        OrderHistoryDTO result = orderHistoryMapper.toDto(orderHistory);
        orderHistorySearchRepository.save(orderHistory);
        return result;
    }

    /**
     * Get all the orderHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrderHistories");
        return orderHistoryRepository.findAll(pageable)
            .map(orderHistoryMapper::toDto);
    }


    /**
     * Get one orderHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderHistoryDTO> findOne(Long id) {
        log.debug("Request to get OrderHistory : {}", id);
        return orderHistoryRepository.findById(id)
            .map(orderHistoryMapper::toDto);
    }

    /**
     * Delete the orderHistory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderHistory : {}", id);
        orderHistoryRepository.deleteById(id);
        orderHistorySearchRepository.deleteById(id);
    }

    /**
     * Search for the orderHistory corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderHistoryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OrderHistories for query {}", query);
        return orderHistorySearchRepository.search(queryStringQuery(query), pageable)
            .map(orderHistoryMapper::toDto);
    }
}
