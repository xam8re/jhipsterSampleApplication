package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.TwitterKey;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TwitterKey entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TwitterKeyRepository extends JpaRepository<TwitterKey, Long> {

}
