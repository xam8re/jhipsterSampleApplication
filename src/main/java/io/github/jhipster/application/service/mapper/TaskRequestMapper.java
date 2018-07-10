package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.TaskRequestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TaskRequest and its DTO TaskRequestDTO.
 */
@Mapper(componentModel = "spring", uses = {ServiceRequestMapper.class, TechnologyMapper.class, MaterialMapper.class, DimensionMapper.class})
public interface TaskRequestMapper extends EntityMapper<TaskRequestDTO, TaskRequest> {

    @Mapping(source = "serviceRequest.id", target = "serviceRequestId")
    @Mapping(source = "technology.id", target = "technologyId")
    @Mapping(source = "material.id", target = "materialId")
    @Mapping(source = "volume.id", target = "volumeId")
    TaskRequestDTO toDto(TaskRequest taskRequest);

    @Mapping(source = "serviceRequestId", target = "serviceRequest")
    @Mapping(source = "technologyId", target = "technology")
    @Mapping(source = "materialId", target = "material")
    @Mapping(source = "volumeId", target = "volume")
    @Mapping(target = "offers", ignore = true)
    TaskRequest toEntity(TaskRequestDTO taskRequestDTO);

    default TaskRequest fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setId(id);
        return taskRequest;
    }
}
