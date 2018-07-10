package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.MachineModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MachineModel entity.
 */
public interface MachineModelSearchRepository extends ElasticsearchRepository<MachineModel, Long> {
}
