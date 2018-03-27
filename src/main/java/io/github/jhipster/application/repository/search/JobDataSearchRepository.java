package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.JobData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the JobData entity.
 */
public interface JobDataSearchRepository extends ElasticsearchRepository<JobData, Long> {
}
