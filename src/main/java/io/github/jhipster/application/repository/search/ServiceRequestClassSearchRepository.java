package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.ServiceRequestClass;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ServiceRequestClass entity.
 */
public interface ServiceRequestClassSearchRepository extends ElasticsearchRepository<ServiceRequestClass, Long> {
}
