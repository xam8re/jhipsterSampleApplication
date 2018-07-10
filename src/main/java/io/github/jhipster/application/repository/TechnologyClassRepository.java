package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.TechnologyClass;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TechnologyClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TechnologyClassRepository extends JpaRepository<TechnologyClass, Long> {

}
