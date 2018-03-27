package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A HodKey.
 */
@Entity
@Table(name = "hod_key")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hodkey")
public class HodKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "hodkey", nullable = false)
    private String hodkey;

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

    public HodKey name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHodkey() {
        return hodkey;
    }

    public HodKey hodkey(String hodkey) {
        this.hodkey = hodkey;
        return this;
    }

    public void setHodkey(String hodkey) {
        this.hodkey = hodkey;
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
        HodKey hodKey = (HodKey) o;
        if (hodKey.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hodKey.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HodKey{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", hodkey='" + getHodkey() + "'" +
            "}";
    }
}
