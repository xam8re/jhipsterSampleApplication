package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.ServiceOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ServiceOrder entity.
 */
public interface ServiceOrderSearchRepository extends ElasticsearchRepository<ServiceOrder, Long> {
}
