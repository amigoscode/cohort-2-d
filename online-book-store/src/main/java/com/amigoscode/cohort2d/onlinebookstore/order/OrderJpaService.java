package com.amigoscode.cohort2d.onlinebookstore.order;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderJpaService implements OrderDAO{

    private final OrderRepository orderRepository;

    public OrderJpaService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.getOrdersByUserId(userId);
    }

}
