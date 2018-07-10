package io.github.jhipster.application.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of TaskRequestSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TaskRequestSearchRepositoryMockConfiguration {

    @MockBean
    private TaskRequestSearchRepository mockTaskRequestSearchRepository;

}
