package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.TechnologyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Technology and its DTO TechnologyDTO.
 */
@Mapper(componentModel = "spring", uses = {TechnologyClassMapper.class, MachineModelMapper.class})
public interface TechnologyMapper extends EntityMapper<TechnologyDTO, Technology> {

    @Mapping(source = "technologyClass.id", target = "technologyClassId")
    @Mapping(source = "machineModel.id", target = "machineModelId")
    TechnologyDTO toDto(Technology technology);

    @Mapping(source = "technologyClassId", target = "technologyClass")
    @Mapping(source = "machineModelId", target = "machineModel")
    Technology toEntity(TechnologyDTO technologyDTO);

    default Technology fromId(Long id) {
        if (id == null) {
            return null;
        }
        Technology technology = new Technology();
        technology.setId(id);
        return technology;
    }
}
