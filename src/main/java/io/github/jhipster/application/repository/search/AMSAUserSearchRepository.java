package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.AMSAUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AMSAUser entity.
 */
public interface AMSAUserSearchRepository extends ElasticsearchRepository<AMSAUser, Long> {
}
