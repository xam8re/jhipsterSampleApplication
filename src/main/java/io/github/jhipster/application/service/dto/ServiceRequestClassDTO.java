package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ServiceRequestClass entity.
 */
public class ServiceRequestClassDTO implements Serializable {

    private Long id;

    private String name;

    private Boolean showOriginalCAD;

    private Boolean showDesignSpaceCAD;

    private Boolean showNonDesignSpaceCAD;

    private Boolean showTechSpec;

    private Boolean showMaterial;

    private Boolean showDimensions;

    private Boolean showPhoto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isShowOriginalCAD() {
        return showOriginalCAD;
    }

    public void setShowOriginalCAD(Boolean showOriginalCAD) {
        this.showOriginalCAD = showOriginalCAD;
    }

    public Boolean isShowDesignSpaceCAD() {
        return showDesignSpaceCAD;
    }

    public void setShowDesignSpaceCAD(Boolean showDesignSpaceCAD) {
        this.showDesignSpaceCAD = showDesignSpaceCAD;
    }

    public Boolean isShowNonDesignSpaceCAD() {
        return showNonDesignSpaceCAD;
    }

    public void setShowNonDesignSpaceCAD(Boolean showNonDesignSpaceCAD) {
        this.showNonDesignSpaceCAD = showNonDesignSpaceCAD;
    }

    public Boolean isShowTechSpec() {
        return showTechSpec;
    }

    public void setShowTechSpec(Boolean showTechSpec) {
        this.showTechSpec = showTechSpec;
    }

    public Boolean isShowMaterial() {
        return showMaterial;
    }

    public void setShowMaterial(Boolean showMaterial) {
        this.showMaterial = showMaterial;
    }

    public Boolean isShowDimensions() {
        return showDimensions;
    }

    public void setShowDimensions(Boolean showDimensions) {
        this.showDimensions = showDimensions;
    }

    public Boolean isShowPhoto() {
        return showPhoto;
    }

    public void setShowPhoto(Boolean showPhoto) {
        this.showPhoto = showPhoto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceRequestClassDTO serviceRequestClassDTO = (ServiceRequestClassDTO) o;
        if (serviceRequestClassDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceRequestClassDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceRequestClassDTO{" +
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
