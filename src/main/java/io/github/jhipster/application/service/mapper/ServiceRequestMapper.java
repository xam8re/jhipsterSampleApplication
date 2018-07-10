package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ServiceRequestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ServiceRequest and its DTO ServiceRequestDTO.
 */
@Mapper(componentModel = "spring", uses = {MaterialMapper.class, DimensionMapper.class, ServiceRequestClassMapper.class, AMSAUserMapper.class, DocumentMapper.class})
public interface ServiceRequestMapper extends EntityMapper<ServiceRequestDTO, ServiceRequest> {

    @Mapping(source = "material.id", target = "materialId")
    @Mapping(source = "volume.id", target = "volumeId")
    @Mapping(source = "srvclass.id", target = "srvclassId")
    @Mapping(source = "amsaUser.id", target = "amsaUserId")
    @Mapping(source = "amsaUser.firstName", target = "amsaUserFirstName")
    ServiceRequestDTO toDto(ServiceRequest serviceRequest);

    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "offers", ignore = true)
    @Mapping(source = "materialId", target = "material")
    @Mapping(source = "volumeId", target = "volume")
    @Mapping(source = "srvclassId", target = "srvclass")
    @Mapping(source = "amsaUserId", target = "amsaUser")
    ServiceRequest toEntity(ServiceRequestDTO serviceRequestDTO);

    default ServiceRequest fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setId(id);
        return serviceRequest;
    }
}
