package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.TaskOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TaskOrder and its DTO TaskOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaskOrderMapper extends EntityMapper<TaskOrderDTO, TaskOrder> {


    @Mapping(target = "orderHistories", ignore = true)
    TaskOrder toEntity(TaskOrderDTO taskOrderDTO);

    default TaskOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaskOrder taskOrder = new TaskOrder();
        taskOrder.setId(id);
        return taskOrder;
    }
}
