package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.TweetTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TweetTemplate entity.
 */
public interface TweetTemplateSearchRepository extends ElasticsearchRepository<TweetTemplate, Long> {
}
