package com.amigoscode.cohort2d.onlinebookstore.order;

import com.amigoscode.cohort2d.onlinebookstore.address.AddressDto;
import com.amigoscode.cohort2d.onlinebookstore.user.UserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderDTO(
        Long id,
        @NotNull
        LocalDateTime orderDate,
        @NotBlank
        String orderTrackingNumber,
        @NotNull
        BigDecimal totalAmount,
        @NotNull
        Integer totalQuantity,
        @NotBlank
        OrderStatus orderStatus,
        @NotEmpty
        Set<OrderItemDTO> orderItems,
        UserDto user,
        AddressDto shippingAddress,
        AddressDto billingAddress
) {
}
