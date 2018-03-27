package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.Sentiment;

/**
 * A Tweet.
 */
@Entity
@Table(name = "tweet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tweet")
public class Tweet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tweet_id", nullable = false)
    private String tweetId;

    @Column(name = "tweet_data")
    private Instant tweetData;

    @Column(name = "tweet_geo")
    private String tweetGeo;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Column(name = "userid", nullable = false)
    private String userid;

    @Enumerated(EnumType.STRING)
    @Column(name = "sentiment")
    private Sentiment sentiment;

    @ManyToOne
    private TweetCategory categoria;

    @ManyToOne
    private TweetTemplate tenplate;

    @ManyToOne
    private Job job;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTweetId() {
        return tweetId;
    }

    public Tweet tweetId(String tweetId) {
        this.tweetId = tweetId;
        return this;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public Instant getTweetData() {
        return tweetData;
    }

    public Tweet tweetData(Instant tweetData) {
        this.tweetData = tweetData;
        return this;
    }

    public void setTweetData(Instant tweetData) {
        this.tweetData = tweetData;
    }

    public String getTweetGeo() {
        return tweetGeo;
    }

    public Tweet tweetGeo(String tweetGeo) {
        this.tweetGeo = tweetGeo;
        return this;
    }

    public void setTweetGeo(String tweetGeo) {
        this.tweetGeo = tweetGeo;
    }

    public String getContent() {
        return content;
    }

    public Tweet content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserid() {
        return userid;
    }

    public Tweet userid(String userid) {
        this.userid = userid;
        return this;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public Tweet sentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
        return this;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public TweetCategory getCategoria() {
        return categoria;
    }

    public Tweet categoria(TweetCategory tweetCategory) {
        this.categoria = tweetCategory;
        return this;
    }

    public void setCategoria(TweetCategory tweetCategory) {
        this.categoria = tweetCategory;
    }

    public TweetTemplate getTenplate() {
        return tenplate;
    }

    public Tweet tenplate(TweetTemplate tweetTemplate) {
        this.tenplate = tweetTemplate;
        return this;
    }

    public void setTenplate(TweetTemplate tweetTemplate) {
        this.tenplate = tweetTemplate;
    }

    public Job getJob() {
        return job;
    }

    public Tweet job(Job job) {
        this.job = job;
        return this;
    }

    public void setJob(Job job) {
        this.job = job;
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
        Tweet tweet = (Tweet) o;
        if (tweet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tweet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tweet{" +
            "id=" + getId() +
            ", tweetId='" + getTweetId() + "'" +
            ", tweetData='" + getTweetData() + "'" +
            ", tweetGeo='" + getTweetGeo() + "'" +
            ", content='" + getContent() + "'" +
            ", userid='" + getUserid() + "'" +
            ", sentiment='" + getSentiment() + "'" +
            "}";
    }
}
