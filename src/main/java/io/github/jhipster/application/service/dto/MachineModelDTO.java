package io.github.jhipster.application.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MachineModel entity.
 */
public class MachineModelDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Long manufacturerId;

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

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MachineModelDTO machineModelDTO = (MachineModelDTO) o;
        if (machineModelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), machineModelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MachineModelDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", manufacturer=" + getManufacturerId() +
            "}";
    }
}
