package com.teb.teborchestrator.mapper;

import com.teb.teborchestrator.model.dto.OrderDto;
import com.teb.teborchestrator.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order mapOrderDtoToOrder(OrderDto orderDto);

    OrderDto mapOrderToOrderDto(Order order);

}
