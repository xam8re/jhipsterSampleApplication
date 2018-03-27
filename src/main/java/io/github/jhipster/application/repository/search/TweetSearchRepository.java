package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Tweet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Tweet entity.
 */
public interface TweetSearchRepository extends ElasticsearchRepository<Tweet, Long> {
}
