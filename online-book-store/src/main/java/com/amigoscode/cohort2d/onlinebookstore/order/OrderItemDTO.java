package com.amigoscode.cohort2d.onlinebookstore.order;

import com.amigoscode.cohort2d.onlinebookstore.book.BookDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderItemDTO(
        Long id,
        BigDecimal price,
        @NotNull
        Integer quantity,
        @NotBlank
        BookDTO book
) {
}
