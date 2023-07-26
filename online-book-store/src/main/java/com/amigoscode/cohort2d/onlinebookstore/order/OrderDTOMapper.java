package com.amigoscode.cohort2d.onlinebookstore.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface OrderDTOMapper {

    OrderDTOMapper INSTANCE = Mappers.getMapper(OrderDTOMapper.class);

    @Mapping(target = "user", ignore = true)
    OrderDTO modelToDTO(Order order);

    List<OrderDTO> modelToDTO(Iterable<Order> orders);

    Order dtoToModel(OrderDTO orderDTO);

    List<Order> dtoToModel(Iterable<OrderDTO> orderDTOS);
}
