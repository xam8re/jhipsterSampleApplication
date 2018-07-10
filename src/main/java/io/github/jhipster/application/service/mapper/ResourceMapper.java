package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ResourceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Resource and its DTO ResourceDTO.
 */
@Mapper(componentModel = "spring", uses = {MachineModelMapper.class, DimensionMapper.class, AMSAUserMapper.class})
public interface ResourceMapper extends EntityMapper<ResourceDTO, Resource> {

    @Mapping(source = "machineModel.id", target = "machineModelId")
    @Mapping(source = "workVolume.id", target = "workVolumeId")
    @Mapping(source = "owner.id", target = "ownerId")
    ResourceDTO toDto(Resource resource);

    @Mapping(target = "materials", ignore = true)
    @Mapping(source = "machineModelId", target = "machineModel")
    @Mapping(source = "workVolumeId", target = "workVolume")
    @Mapping(source = "ownerId", target = "owner")
    Resource toEntity(ResourceDTO resourceDTO);

    default Resource fromId(Long id) {
        if (id == null) {
            return null;
        }
        Resource resource = new Resource();
        resource.setId(id);
        return resource;
    }
}
