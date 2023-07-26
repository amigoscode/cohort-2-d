package com.amigoscode.cohort2d.onlinebookstore.order;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface OrderItemDTOMapper {

    OrderItemDTOMapper INSTANCE = Mappers.getMapper(OrderItemDTOMapper.class);


    OrderItemDTO modelToDTO(OrderItem orderItem);

    List<OrderItemDTO> modelToDTO(Iterable<OrderItem> orderItems);

    OrderItem dtoToModel(OrderItemDTO orderItemDTO);

    List<OrderItem> dtoToModel(Iterable<OrderItemDTO> orderItemDTOS);
}
