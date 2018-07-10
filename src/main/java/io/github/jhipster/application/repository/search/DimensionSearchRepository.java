package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Dimension;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Dimension entity.
 */
public interface DimensionSearchRepository extends ElasticsearchRepository<Dimension, Long> {
}
