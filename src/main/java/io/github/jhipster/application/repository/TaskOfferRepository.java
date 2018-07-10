package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.TaskOffer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TaskOffer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskOfferRepository extends JpaRepository<TaskOffer, Long> {

}
