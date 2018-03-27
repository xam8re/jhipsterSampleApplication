package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TweetTemplate.
 */
@Entity
@Table(name = "tweet_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tweettemplate")
public class TweetTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "pattern", nullable = false)
    private String pattern;

    @Column(name = "descrizione")
    private String descrizione;

    @ManyToOne
    private TwitterKey twitterKey;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPattern() {
        return pattern;
    }

    public TweetTemplate pattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public TweetTemplate descrizione(String descrizione) {
        this.descrizione = descrizione;
        return this;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public TwitterKey getTwitterKey() {
        return twitterKey;
    }

    public TweetTemplate twitterKey(TwitterKey twitterKey) {
        this.twitterKey = twitterKey;
        return this;
    }

    public void setTwitterKey(TwitterKey twitterKey) {
        this.twitterKey = twitterKey;
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
        TweetTemplate tweetTemplate = (TweetTemplate) o;
        if (tweetTemplate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tweetTemplate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TweetTemplate{" +
            "id=" + getId() +
            ", pattern='" + getPattern() + "'" +
            ", descrizione='" + getDescrizione() + "'" +
            "}";
    }
}
