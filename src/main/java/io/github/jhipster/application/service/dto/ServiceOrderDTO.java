package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.OrderState;

/**
 * A DTO for the ServiceOrder entity.
 */
public class ServiceOrderDTO implements Serializable {

    private Long id;

    private OrderState state;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceOrderDTO serviceOrderDTO = (ServiceOrderDTO) o;
        if (serviceOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceOrderDTO{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            "}";
    }
}
