package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Document;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Document entity.
 */
public interface DocumentSearchRepository extends ElasticsearchRepository<Document, Long> {
}
