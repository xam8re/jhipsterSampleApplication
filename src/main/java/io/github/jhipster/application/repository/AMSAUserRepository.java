package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.AMSAUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AMSAUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AMSAUserRepository extends JpaRepository<AMSAUser, Long> {

}
