package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Technology;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Technology entity.
 */
public interface TechnologySearchRepository extends ElasticsearchRepository<Technology, Long> {
}
