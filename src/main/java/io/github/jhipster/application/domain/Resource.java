package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Resource.
 */
@Entity
@Table(name = "resource")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "resource")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accuracy")
    private String accuracy;

    /**
     * equivalente a scanning on site
     */
    @ApiModelProperty(value = "equivalente a scanning on site")
    @Column(name = "portable")
    private Boolean portable;

    @OneToMany(mappedBy = "resource")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Material> materials = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private MachineModel machineModel;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Dimension workVolume;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private AMSAUser owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public Resource accuracy(String accuracy) {
        this.accuracy = accuracy;
        return this;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public Boolean isPortable() {
        return portable;
    }

    public Resource portable(Boolean portable) {
        this.portable = portable;
        return this;
    }

    public void setPortable(Boolean portable) {
        this.portable = portable;
    }

    public Set<Material> getMaterials() {
        return materials;
    }

    public Resource materials(Set<Material> materials) {
        this.materials = materials;
        return this;
    }

    public Resource addMaterial(Material material) {
        this.materials.add(material);
        material.setResource(this);
        return this;
    }

    public Resource removeMaterial(Material material) {
        this.materials.remove(material);
        material.setResource(null);
        return this;
    }

    public void setMaterials(Set<Material> materials) {
        this.materials = materials;
    }

    public MachineModel getMachineModel() {
        return machineModel;
    }

    public Resource machineModel(MachineModel machineModel) {
        this.machineModel = machineModel;
        return this;
    }

    public void setMachineModel(MachineModel machineModel) {
        this.machineModel = machineModel;
    }

    public Dimension getWorkVolume() {
        return workVolume;
    }

    public Resource workVolume(Dimension dimension) {
        this.workVolume = dimension;
        return this;
    }

    public void setWorkVolume(Dimension dimension) {
        this.workVolume = dimension;
    }

    public AMSAUser getOwner() {
        return owner;
    }

    public Resource owner(AMSAUser aMSAUser) {
        this.owner = aMSAUser;
        return this;
    }

    public void setOwner(AMSAUser aMSAUser) {
        this.owner = aMSAUser;
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
        Resource resource = (Resource) o;
        if (resource.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resource.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Resource{" +
            "id=" + getId() +
            ", accuracy='" + getAccuracy() + "'" +
            ", portable='" + isPortable() + "'" +
            "}";
    }
}
