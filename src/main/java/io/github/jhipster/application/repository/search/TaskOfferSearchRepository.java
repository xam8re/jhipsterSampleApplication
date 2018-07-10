package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.TaskOffer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TaskOffer entity.
 */
public interface TaskOfferSearchRepository extends ElasticsearchRepository<TaskOffer, Long> {
}
