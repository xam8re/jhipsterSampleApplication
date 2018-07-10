package io.github.jhipster.application.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * Service Class is
 * - Nuovo Prodotto
 * - Piccola Serie
 * - Parti di Ricambio
 */
@ApiModel(description = "Service Class is - Nuovo Prodotto - Piccola Serie - Parti di Ricambio")
@Entity
@Table(name = "service_request_class")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "servicerequestclass")
public class ServiceRequestClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "show_original_cad")
    private Boolean showOriginalCAD;

    @Column(name = "show_design_space_cad")
    private Boolean showDesignSpaceCAD;

    @Column(name = "show_non_design_space_cad")
    private Boolean showNonDesignSpaceCAD;

    @Column(name = "show_tech_spec")
    private Boolean showTechSpec;

    @Column(name = "show_material")
    private Boolean showMaterial;

    @Column(name = "show_dimensions")
    private Boolean showDimensions;

    @Column(name = "show_photo")
    private Boolean showPhoto;

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

    public ServiceRequestClass name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isShowOriginalCAD() {
        return showOriginalCAD;
    }

    public ServiceRequestClass showOriginalCAD(Boolean showOriginalCAD) {
        this.showOriginalCAD = showOriginalCAD;
        return this;
    }

    public void setShowOriginalCAD(Boolean showOriginalCAD) {
        this.showOriginalCAD = showOriginalCAD;
    }

    public Boolean isShowDesignSpaceCAD() {
        return showDesignSpaceCAD;
    }

    public ServiceRequestClass showDesignSpaceCAD(Boolean showDesignSpaceCAD) {
        this.showDesignSpaceCAD = showDesignSpaceCAD;
        return this;
    }

    public void setShowDesignSpaceCAD(Boolean showDesignSpaceCAD) {
        this.showDesignSpaceCAD = showDesignSpaceCAD;
    }

    public Boolean isShowNonDesignSpaceCAD() {
        return showNonDesignSpaceCAD;
    }

    public ServiceRequestClass showNonDesignSpaceCAD(Boolean showNonDesignSpaceCAD) {
        this.showNonDesignSpaceCAD = showNonDesignSpaceCAD;
        return this;
    }

    public void setShowNonDesignSpaceCAD(Boolean showNonDesignSpaceCAD) {
        this.showNonDesignSpaceCAD = showNonDesignSpaceCAD;
    }

    public Boolean isShowTechSpec() {
        return showTechSpec;
    }

    public ServiceRequestClass showTechSpec(Boolean showTechSpec) {
        this.showTechSpec = showTechSpec;
        return this;
    }

    public void setShowTechSpec(Boolean showTechSpec) {
        this.showTechSpec = showTechSpec;
    }

    public Boolean isShowMaterial() {
        return showMaterial;
    }

    public ServiceRequestClass showMaterial(Boolean showMaterial) {
        this.showMaterial = showMaterial;
        return this;
    }

    public void setShowMaterial(Boolean showMaterial) {
        this.showMaterial = showMaterial;
    }

    public Boolean isShowDimensions() {
        return showDimensions;
    }

    public ServiceRequestClass showDimensions(Boolean showDimensions) {
        this.showDimensions = showDimensions;
        return this;
    }

    public void setShowDimensions(Boolean showDimensions) {
        this.showDimensions = showDimensions;
    }

    public Boolean isShowPhoto() {
        return showPhoto;
    }

    public ServiceRequestClass showPhoto(Boolean showPhoto) {
        this.showPhoto = showPhoto;
        return this;
    }

    public void setShowPhoto(Boolean showPhoto) {
        this.showPhoto = showPhoto;
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
        ServiceRequestClass serviceRequestClass = (ServiceRequestClass) o;
        if (serviceRequestClass.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceRequestClass.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceRequestClass{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", showOriginalCAD='" + isShowOriginalCAD() + "'" +
            ", showDesignSpaceCAD='" + isShowDesignSpaceCAD() + "'" +
            ", showNonDesignSpaceCAD='" + isShowNonDesignSpaceCAD() + "'" +
            ", showTechSpec='" + isShowTechSpec() + "'" +
            ", showMaterial='" + isShowMaterial() + "'" +
            ", showDimensions='" + isShowDimensions() + "'" +
            ", showPhoto='" + isShowPhoto() + "'" +
            "}";
    }
}
