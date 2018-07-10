package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Material;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Material entity.
 */
public interface MaterialSearchRepository extends ElasticsearchRepository<Material, Long> {
}
