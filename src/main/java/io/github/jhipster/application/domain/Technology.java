package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Technology.
 */
@Entity
@Table(name = "technology")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "technology")
public class Technology implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(unique = true)
    private TechnologyClass technologyClass;

    @ManyToOne
    @JsonIgnoreProperties("technologies")
    private MachineModel machineModel;

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

    public Technology name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TechnologyClass getTechnologyClass() {
        return technologyClass;
    }

    public Technology technologyClass(TechnologyClass technologyClass) {
        this.technologyClass = technologyClass;
        return this;
    }

    public void setTechnologyClass(TechnologyClass technologyClass) {
        this.technologyClass = technologyClass;
    }

    public MachineModel getMachineModel() {
        return machineModel;
    }

    public Technology machineModel(MachineModel machineModel) {
        this.machineModel = machineModel;
        return this;
    }

    public void setMachineModel(MachineModel machineModel) {
        this.machineModel = machineModel;
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
        Technology technology = (Technology) o;
        if (technology.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), technology.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Technology{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
