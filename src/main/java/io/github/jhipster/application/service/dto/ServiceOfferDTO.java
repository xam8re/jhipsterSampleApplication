package io.github.jhipster.application.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.OfferState;

/**
 * A DTO for the ServiceOffer entity.
 */
public class ServiceOfferDTO implements Serializable {

    private Long id;

    private Double prize;

    private OfferState state;

    private Instant period;

    private Long serviceRequestId;

    private Long amsaUserId;

    private String amsaUserFirstName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrize() {
        return prize;
    }

    public void setPrize(Double prize) {
        this.prize = prize;
    }

    public OfferState getState() {
        return state;
    }

    public void setState(OfferState state) {
        this.state = state;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceOfferDTO serviceOfferDTO = (ServiceOfferDTO) o;
        if (serviceOfferDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceOfferDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceOfferDTO{" +
            "id=" + getId() +
            ", prize=" + getPrize() +
            ", state='" + getState() + "'" +
            ", period='" + getPeriod() + "'" +
            ", serviceRequest=" + getServiceRequestId() +
            ", amsaUser=" + getAmsaUserId() +
            ", amsaUser='" + getAmsaUserFirstName() + "'" +
            "}";
    }
}
