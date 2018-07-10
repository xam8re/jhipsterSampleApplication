package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ServiceOffer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceOffer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceOfferRepository extends JpaRepository<ServiceOffer, Long> {

}
