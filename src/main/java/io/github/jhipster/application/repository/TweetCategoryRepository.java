package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.TweetCategory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TweetCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TweetCategoryRepository extends JpaRepository<TweetCategory, Long> {

}
