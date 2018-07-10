package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ServiceOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ServiceOrder and its DTO ServiceOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceOrderMapper extends EntityMapper<ServiceOrderDTO, ServiceOrder> {


    @Mapping(target = "orderHistories", ignore = true)
    ServiceOrder toEntity(ServiceOrderDTO serviceOrderDTO);

    default ServiceOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setId(id);
        return serviceOrder;
    }
}
