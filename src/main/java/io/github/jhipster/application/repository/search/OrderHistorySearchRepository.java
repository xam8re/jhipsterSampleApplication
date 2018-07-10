package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.OrderHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OrderHistory entity.
 */
public interface OrderHistorySearchRepository extends ElasticsearchRepository<OrderHistory, Long> {
}
