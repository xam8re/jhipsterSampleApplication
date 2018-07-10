package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.TaskRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TaskRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskRequestRepository extends JpaRepository<TaskRequest, Long> {

}
