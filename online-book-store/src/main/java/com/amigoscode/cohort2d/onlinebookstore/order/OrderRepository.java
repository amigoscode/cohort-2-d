package com.amigoscode.cohort2d.onlinebookstore.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> getOrdersByUserId(Long userId);
}
