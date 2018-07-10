package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.DimensionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Dimension and its DTO DimensionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DimensionMapper extends EntityMapper<DimensionDTO, Dimension> {



    default Dimension fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dimension dimension = new Dimension();
        dimension.setId(id);
        return dimension;
    }
}
