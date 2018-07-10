package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.application.domain.enumeration.UnitSystem;

/**
 * A DTO for the Dimension entity.
 */
public class DimensionDTO implements Serializable {

    private Long id;

    private UnitSystem us;

    private Double x;

    private Double y;

    private Double z;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UnitSystem getUs() {
        return us;
    }

    public void setUs(UnitSystem us) {
        this.us = us;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DimensionDTO dimensionDTO = (DimensionDTO) o;
        if (dimensionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dimensionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DimensionDTO{" +
            "id=" + getId() +
            ", us='" + getUs() + "'" +
            ", x=" + getX() +
            ", y=" + getY() +
            ", z=" + getZ() +
            "}";
    }
}
