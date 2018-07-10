package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ServiceOfferDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ServiceOffer and its DTO ServiceOfferDTO.
 */
@Mapper(componentModel = "spring", uses = {ServiceRequestMapper.class, AMSAUserMapper.class})
public interface ServiceOfferMapper extends EntityMapper<ServiceOfferDTO, ServiceOffer> {

    @Mapping(source = "serviceRequest.id", target = "serviceRequestId")
    @Mapping(source = "amsaUser.id", target = "amsaUserId")
    @Mapping(source = "amsaUser.firstName", target = "amsaUserFirstName")
    ServiceOfferDTO toDto(ServiceOffer serviceOffer);

    @Mapping(source = "serviceRequestId", target = "serviceRequest")
    @Mapping(target = "chosenoffers", ignore = true)
    @Mapping(source = "amsaUserId", target = "amsaUser")
    ServiceOffer toEntity(ServiceOfferDTO serviceOfferDTO);

    default ServiceOffer fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceOffer serviceOffer = new ServiceOffer();
        serviceOffer.setId(id);
        return serviceOffer;
    }
}
