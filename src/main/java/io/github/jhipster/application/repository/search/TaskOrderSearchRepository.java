package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.TaskOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TaskOrder entity.
 */
public interface TaskOrderSearchRepository extends ElasticsearchRepository<TaskOrder, Long> {
}
