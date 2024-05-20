package com.teb.orderservice.mapper;

import com.teb.orderservice.model.dto.OrderDto;
import com.teb.orderservice.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto mapOrderToOrderDto(Order order);

}
