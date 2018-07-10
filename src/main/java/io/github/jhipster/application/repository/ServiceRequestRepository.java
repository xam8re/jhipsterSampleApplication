package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ServiceRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ServiceRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

    @Query(value = "select distinct service_request from ServiceRequest service_request left join fetch service_request.documents",
        countQuery = "select count(distinct service_request) from ServiceRequest service_request")
    Page<ServiceRequest> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct service_request from ServiceRequest service_request left join fetch service_request.documents")
    List<ServiceRequest> findAllWithEagerRelationships();

    @Query("select service_request from ServiceRequest service_request left join fetch service_request.documents where service_request.id =:id")
    Optional<ServiceRequest> findOneWithEagerRelationships(@Param("id") Long id);

}
