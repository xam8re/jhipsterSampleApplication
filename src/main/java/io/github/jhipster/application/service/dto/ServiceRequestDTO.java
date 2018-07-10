package io.github.jhipster.application.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ServiceRequest entity.
 */
public class ServiceRequestDTO implements Serializable {

    private Long id;

    private String name;

    private Instant period;

    private Long materialId;

    private Long volumeId;

    private Long srvclassId;

    private Long amsaUserId;

    private String amsaUserFirstName;

    private Set<DocumentDTO> documents = new HashSet<>();

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

    public Instant getPeriod() {
        return period;
    }

    public void setPeriod(Instant period) {
        this.period = period;
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

    public Long getSrvclassId() {
        return srvclassId;
    }

    public void setSrvclassId(Long serviceRequestClassId) {
        this.srvclassId = serviceRequestClassId;
    }

    public Long getAmsaUserId() {
        return amsaUserId;
    }

    public void setAmsaUserId(Long aMSAUserId) {
        this.amsaUserId = aMSAUserId;
    }

    public String getAmsaUserFirstName() {
        return amsaUserFirstName;
    }

    public void setAmsaUserFirstName(String aMSAUserFirstName) {
        this.amsaUserFirstName = aMSAUserFirstName;
    }

    public Set<DocumentDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<DocumentDTO> documents) {
        this.documents = documents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceRequestDTO serviceRequestDTO = (ServiceRequestDTO) o;
        if (serviceRequestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceRequestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceRequestDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", period='" + getPeriod() + "'" +
            ", material=" + getMaterialId() +
            ", volume=" + getVolumeId() +
            ", srvclass=" + getSrvclassId() +
            ", amsaUser=" + getAmsaUserId() +
            ", amsaUser='" + getAmsaUserFirstName() + "'" +
            "}";
    }
}
