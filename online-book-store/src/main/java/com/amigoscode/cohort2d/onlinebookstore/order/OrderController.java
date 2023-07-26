package com.amigoscode.cohort2d.onlinebookstore.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("user/{userId}")
    public List<OrderDTO> getOrdersByUserId(@PathVariable("userId") Long userId) {
        var all = orderService.getOrdersByUserId(userId);
        return all;
    }

    @PostMapping
    public OrderDTO placeOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.placeOrder(orderDTO);
    }

}
