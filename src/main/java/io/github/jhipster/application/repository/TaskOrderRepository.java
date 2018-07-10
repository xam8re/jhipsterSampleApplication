package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.TaskOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TaskOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskOrderRepository extends JpaRepository<TaskOrder, Long> {

}
