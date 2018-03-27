package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TwitterKey.
 */
@Entity
@Table(name = "twitter_key")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "twitterkey")
public class TwitterKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "consumer_key", nullable = false)
    private String consumerKey;

    @NotNull
    @Column(name = "consumer_secret", nullable = false)
    private String consumerSecret;

    @NotNull
    @Column(name = "access_token", nullable = false)
    private String accessToken;

    @NotNull
    @Column(name = "access_token_secret", nullable = false)
    private String accessTokenSecret;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TwitterKey name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public TwitterKey consumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
        return this;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public TwitterKey consumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
        return this;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public TwitterKey accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public TwitterKey accessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
        return this;
    }

    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }

    public Boolean isActive() {
        return active;
    }

    public TwitterKey active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
        TwitterKey twitterKey = (TwitterKey) o;
        if (twitterKey.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), twitterKey.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TwitterKey{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", consumerKey='" + getConsumerKey() + "'" +
            ", consumerSecret='" + getConsumerSecret() + "'" +
            ", accessToken='" + getAccessToken() + "'" +
            ", accessTokenSecret='" + getAccessTokenSecret() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
