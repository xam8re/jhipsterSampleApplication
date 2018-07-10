package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TechnologyClass.
 */
@Entity
@Table(name = "technology_class")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "technologyclass")
public class TechnologyClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "show_accuracy")
    private Boolean showAccuracy;

    @Column(name = "show_portable")
    private Boolean showPortable;

    @Column(name = "show_material")
    private Boolean showMaterial;

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

    public TechnologyClass name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isShowAccuracy() {
        return showAccuracy;
    }

    public TechnologyClass showAccuracy(Boolean showAccuracy) {
        this.showAccuracy = showAccuracy;
        return this;
    }

    public void setShowAccuracy(Boolean showAccuracy) {
        this.showAccuracy = showAccuracy;
    }

    public Boolean isShowPortable() {
        return showPortable;
    }

    public TechnologyClass showPortable(Boolean showPortable) {
        this.showPortable = showPortable;
        return this;
    }

    public void setShowPortable(Boolean showPortable) {
        this.showPortable = showPortable;
    }

    public Boolean isShowMaterial() {
        return showMaterial;
    }

    public TechnologyClass showMaterial(Boolean showMaterial) {
        this.showMaterial = showMaterial;
        return this;
    }

    public void setShowMaterial(Boolean showMaterial) {
        this.showMaterial = showMaterial;
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
        TechnologyClass technologyClass = (TechnologyClass) o;
        if (technologyClass.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), technologyClass.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TechnologyClass{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", showAccuracy='" + isShowAccuracy() + "'" +
            ", showPortable='" + isShowPortable() + "'" +
            ", showMaterial='" + isShowMaterial() + "'" +
            "}";
    }
}
