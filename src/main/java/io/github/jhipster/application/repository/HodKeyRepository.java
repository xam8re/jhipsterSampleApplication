package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.HodKey;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HodKey entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HodKeyRepository extends JpaRepository<HodKey, Long> {

}
