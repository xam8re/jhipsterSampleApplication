package io.github.jhipster.application.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TechnologyClass entity.
 */
public class TechnologyClassDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Boolean showAccuracy;

    private Boolean showPortable;

    private Boolean showMaterial;

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

    public Boolean isShowAccuracy() {
        return showAccuracy;
    }

    public void setShowAccuracy(Boolean showAccuracy) {
        this.showAccuracy = showAccuracy;
    }

    public Boolean isShowPortable() {
        return showPortable;
    }

    public void setShowPortable(Boolean showPortable) {
        this.showPortable = showPortable;
    }

    public Boolean isShowMaterial() {
        return showMaterial;
    }

    public void setShowMaterial(Boolean showMaterial) {
        this.showMaterial = showMaterial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TechnologyClassDTO technologyClassDTO = (TechnologyClassDTO) o;
        if (technologyClassDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), technologyClassDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TechnologyClassDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", showAccuracy='" + isShowAccuracy() + "'" +
            ", showPortable='" + isShowPortable() + "'" +
            ", showMaterial='" + isShowMaterial() + "'" +
            "}";
    }
}
