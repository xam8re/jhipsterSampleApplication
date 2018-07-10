package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.MachineModelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MachineModel and its DTO MachineModelDTO.
 */
@Mapper(componentModel = "spring", uses = {ManufacturerMapper.class})
public interface MachineModelMapper extends EntityMapper<MachineModelDTO, MachineModel> {

    @Mapping(source = "manufacturer.id", target = "manufacturerId")
    MachineModelDTO toDto(MachineModel machineModel);

    @Mapping(target = "technologies", ignore = true)
    @Mapping(source = "manufacturerId", target = "manufacturer")
    MachineModel toEntity(MachineModelDTO machineModelDTO);

    default MachineModel fromId(Long id) {
        if (id == null) {
            return null;
        }
        MachineModel machineModel = new MachineModel();
        machineModel.setId(id);
        return machineModel;
    }
}
