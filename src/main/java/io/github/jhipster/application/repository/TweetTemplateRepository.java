package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.TweetTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TweetTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TweetTemplateRepository extends JpaRepository<TweetTemplate, Long> {

}
