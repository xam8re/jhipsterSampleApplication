package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.TweetCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TweetCategory entity.
 */
public interface TweetCategorySearchRepository extends ElasticsearchRepository<TweetCategory, Long> {
}
