package com.amigoscode.cohort2d.onlinebookstore.order;

import java.util.List;

public interface OrderDAO {

    Order addOrder(Order order);

    // TODO filter by userId
    List<Order> getOrdersByUserId(Long userId);
}
