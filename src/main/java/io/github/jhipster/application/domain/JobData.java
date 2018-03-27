package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A JobData.
 */
@Entity
@Table(name = "job_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "jobdata")
public class JobData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "n_retweet")
    private Integer nRetweet;

    @Column(name = "n_like")
    private Integer nLike;

    @Column(name = "n_follower")
    private Integer nFollower;

    @Column(name = "n_tweet_user")
    private Integer nTweetUser;

    @ManyToOne
    private Tweet tweet;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getnRetweet() {
        return nRetweet;
    }

    public JobData nRetweet(Integer nRetweet) {
        this.nRetweet = nRetweet;
        return this;
    }

    public void setnRetweet(Integer nRetweet) {
        this.nRetweet = nRetweet;
    }

    public Integer getnLike() {
        return nLike;
    }

    public JobData nLike(Integer nLike) {
        this.nLike = nLike;
        return this;
    }

    public void setnLike(Integer nLike) {
        this.nLike = nLike;
    }

    public Integer getnFollower() {
        return nFollower;
    }

    public JobData nFollower(Integer nFollower) {
        this.nFollower = nFollower;
        return this;
    }

    public void setnFollower(Integer nFollower) {
        this.nFollower = nFollower;
    }

    public Integer getnTweetUser() {
        return nTweetUser;
    }

    public JobData nTweetUser(Integer nTweetUser) {
        this.nTweetUser = nTweetUser;
        return this;
    }

    public void setnTweetUser(Integer nTweetUser) {
        this.nTweetUser = nTweetUser;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public JobData tweet(Tweet tweet) {
        this.tweet = tweet;
        return this;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobData jobData = (JobData) o;
        if (jobData.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobData.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobData{" +
            "id=" + getId() +
            ", nRetweet=" + getnRetweet() +
            ", nLike=" + getnLike() +
            ", nFollower=" + getnFollower() +
            ", nTweetUser=" + getnTweetUser() +
            "}";
    }
}
