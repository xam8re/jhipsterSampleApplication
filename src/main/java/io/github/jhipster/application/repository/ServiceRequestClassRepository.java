package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ServiceRequestClass;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceRequestClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceRequestClassRepository extends JpaRepository<ServiceRequestClass, Long> {

}
