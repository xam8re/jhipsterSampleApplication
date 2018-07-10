package io.github.jhipster.application.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.OrderState;

/**
 * A DTO for the OrderHistory entity.
 */
public class OrderHistoryDTO implements Serializable {

    private Long id;

    private OrderState state;

    private Instant date;

    private Long byId;

    private Long serviceOrderId;

    private Long taskOrderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Long getById() {
        return byId;
    }

    public void setById(Long aMSAUserId) {
        this.byId = aMSAUserId;
    }

    public Long getServiceOrderId() {
        return serviceOrderId;
    }

    public void setServiceOrderId(Long serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public Long getTaskOrderId() {
        return taskOrderId;
    }

    public void setTaskOrderId(Long taskOrderId) {
        this.taskOrderId = taskOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderHistoryDTO orderHistoryDTO = (OrderHistoryDTO) o;
        if (orderHistoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderHistoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderHistoryDTO{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            ", date='" + getDate() + "'" +
            ", by=" + getById() +
            ", serviceOrder=" + getServiceOrderId() +
            ", taskOrder=" + getTaskOrderId() +
            "}";
    }
}
