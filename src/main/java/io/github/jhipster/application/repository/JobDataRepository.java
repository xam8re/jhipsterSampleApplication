package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.JobData;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the JobData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobDataRepository extends JpaRepository<JobData, Long> {

}
