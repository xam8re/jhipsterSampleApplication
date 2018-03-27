package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.HodKey;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the HodKey entity.
 */
public interface HodKeySearchRepository extends ElasticsearchRepository<HodKey, Long> {
}
