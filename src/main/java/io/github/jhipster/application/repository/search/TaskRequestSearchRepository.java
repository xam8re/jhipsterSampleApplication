package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.TaskRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TaskRequest entity.
 */
public interface TaskRequestSearchRepository extends ElasticsearchRepository<TaskRequest, Long> {
}
