package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.TechnologyClass;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TechnologyClass entity.
 */
public interface TechnologyClassSearchRepository extends ElasticsearchRepository<TechnologyClass, Long> {
}
