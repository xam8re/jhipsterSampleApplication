package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.OrderHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderHistory and its DTO OrderHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {AMSAUserMapper.class, ServiceOrderMapper.class, TaskOrderMapper.class})
public interface OrderHistoryMapper extends EntityMapper<OrderHistoryDTO, OrderHistory> {

    @Mapping(source = "by.id", target = "byId")
    @Mapping(source = "serviceOrder.id", target = "serviceOrderId")
    @Mapping(source = "taskOrder.id", target = "taskOrderId")
    OrderHistoryDTO toDto(OrderHistory orderHistory);

    @Mapping(source = "byId", target = "by")
    @Mapping(source = "serviceOrderId", target = "serviceOrder")
    @Mapping(source = "taskOrderId", target = "taskOrder")
    OrderHistory toEntity(OrderHistoryDTO orderHistoryDTO);

    default OrderHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setId(id);
        return orderHistory;
    }
}
