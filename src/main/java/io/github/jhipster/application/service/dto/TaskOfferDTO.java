package io.github.jhipster.application.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.OfferState;

/**
 * A DTO for the TaskOffer entity.
 */
public class TaskOfferDTO implements Serializable {

    private Long id;

    private Double prize;

    private OfferState state;

    private Instant period;

    private Long taskRequestId;

    private Long supplierId;

    private Long serviceOfferId;

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

    public Long getTaskRequestId() {
        return taskRequestId;
    }

    public void setTaskRequestId(Long taskRequestId) {
        this.taskRequestId = taskRequestId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long aMSAUserId) {
        this.supplierId = aMSAUserId;
    }

    public Long getServiceOfferId() {
        return serviceOfferId;
    }

    public void setServiceOfferId(Long serviceOfferId) {
        this.serviceOfferId = serviceOfferId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaskOfferDTO taskOfferDTO = (TaskOfferDTO) o;
        if (taskOfferDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taskOfferDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaskOfferDTO{" +
            "id=" + getId() +
            ", prize=" + getPrize() +
            ", state='" + getState() + "'" +
            ", period='" + getPeriod() + "'" +
            ", taskRequest=" + getTaskRequestId() +
            ", supplier=" + getSupplierId() +
            ", serviceOffer=" + getServiceOfferId() +
            "}";
    }
}
