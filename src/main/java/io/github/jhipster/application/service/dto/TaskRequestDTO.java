package io.github.jhipster.application.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TaskRequest entity.
 */
public class TaskRequestDTO implements Serializable {

    private Long id;

    private String title;

    private String description;

    private Instant period;

    private Long serviceRequestId;

    private Long technologyId;

    private Long materialId;

    private Long volumeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getPeriod() {
        return period;
    }

    public void setPeriod(Instant period) {
        this.period = period;
    }

    public Long getServiceRequestId() {
        return serviceRequestId;
    }

    public void setServiceRequestId(Long serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
    }

    public Long getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(Long technologyId) {
        this.technologyId = technologyId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(Long dimensionId) {
        this.volumeId = dimensionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaskRequestDTO taskRequestDTO = (TaskRequestDTO) o;
        if (taskRequestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taskRequestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaskRequestDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", period='" + getPeriod() + "'" +
            ", serviceRequest=" + getServiceRequestId() +
            ", technology=" + getTechnologyId() +
            ", material=" + getMaterialId() +
            ", volume=" + getVolumeId() +
            "}";
    }
}
