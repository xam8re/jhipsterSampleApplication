package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ServiceRequestClassDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ServiceRequestClass and its DTO ServiceRequestClassDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceRequestClassMapper extends EntityMapper<ServiceRequestClassDTO, ServiceRequestClass> {



    default ServiceRequestClass fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceRequestClass serviceRequestClass = new ServiceRequestClass();
        serviceRequestClass.setId(id);
        return serviceRequestClass;
    }
}
