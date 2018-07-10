package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ServiceOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {

}
