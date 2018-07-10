package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.ServiceOffer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ServiceOffer entity.
 */
public interface ServiceOfferSearchRepository extends ElasticsearchRepository<ServiceOffer, Long> {
}
