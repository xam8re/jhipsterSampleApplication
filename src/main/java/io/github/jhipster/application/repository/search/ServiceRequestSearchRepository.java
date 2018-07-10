package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.ServiceRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ServiceRequest entity.
 */
public interface ServiceRequestSearchRepository extends ElasticsearchRepository<ServiceRequest, Long> {
}
