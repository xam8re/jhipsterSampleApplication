package io.github.jhipster.application.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Resource entity.
 */
public class ResourceDTO implements Serializable {

    private Long id;

    private String accuracy;

    private Boolean portable;

    private Long machineModelId;

    private Long workVolumeId;

    private Long ownerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public Boolean isPortable() {
        return portable;
    }

    public void setPortable(Boolean portable) {
        this.portable = portable;
    }

    public Long getMachineModelId() {
        return machineModelId;
    }

    public void setMachineModelId(Long machineModelId) {
        this.machineModelId = machineModelId;
    }

    public Long getWorkVolumeId() {
        return workVolumeId;
    }

    public void setWorkVolumeId(Long dimensionId) {
        this.workVolumeId = dimensionId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long aMSAUserId) {
        this.ownerId = aMSAUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResourceDTO resourceDTO = (ResourceDTO) o;
        if (resourceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resourceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResourceDTO{" +
            "id=" + getId() +
            ", accuracy='" + getAccuracy() + "'" +
            ", portable='" + isPortable() + "'" +
            ", machineModel=" + getMachineModelId() +
            ", workVolume=" + getWorkVolumeId() +
            ", owner=" + getOwnerId() +
            "}";
    }
}
