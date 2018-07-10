package io.github.jhipster.application.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ServiceOrderSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ServiceOrderSearchRepositoryMockConfiguration {

    @MockBean
    private ServiceOrderSearchRepository mockServiceOrderSearchRepository;

}
