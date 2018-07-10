package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.TaskOfferDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TaskOffer and its DTO TaskOfferDTO.
 */
@Mapper(componentModel = "spring", uses = {TaskRequestMapper.class, AMSAUserMapper.class, ServiceOfferMapper.class})
public interface TaskOfferMapper extends EntityMapper<TaskOfferDTO, TaskOffer> {

    @Mapping(source = "taskRequest.id", target = "taskRequestId")
    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "serviceOffer.id", target = "serviceOfferId")
    TaskOfferDTO toDto(TaskOffer taskOffer);

    @Mapping(source = "taskRequestId", target = "taskRequest")
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "serviceOfferId", target = "serviceOffer")
    TaskOffer toEntity(TaskOfferDTO taskOfferDTO);

    default TaskOffer fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaskOffer taskOffer = new TaskOffer();
        taskOffer.setId(id);
        return taskOffer;
    }
}
