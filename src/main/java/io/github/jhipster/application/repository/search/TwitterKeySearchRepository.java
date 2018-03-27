package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.TwitterKey;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TwitterKey entity.
 */
public interface TwitterKeySearchRepository extends ElasticsearchRepository<TwitterKey, Long> {
}
