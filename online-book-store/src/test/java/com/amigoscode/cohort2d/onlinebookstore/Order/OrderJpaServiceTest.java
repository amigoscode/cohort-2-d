package com.amigoscode.cohort2d.onlinebookstore.Order;

import com.amigoscode.cohort2d.onlinebookstore.order.Order;
import com.amigoscode.cohort2d.onlinebookstore.order.OrderJpaService;
import com.amigoscode.cohort2d.onlinebookstore.order.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderJpaServiceTest {

    @InjectMocks
    private OrderJpaService underTest;

    @Mock
    private OrderRepository orderRepository;

    @Test
    void shouldGetOrdersByUserId() {
        var id = 5L;

        underTest.getOrdersByUserId(id);

        verify(orderRepository).getOrdersByUserId(id);
    }

    @Test
    void shouldPlaceAnOrder() {
        var order = new Order();

        underTest.addOrder(order);

        verify(orderRepository).save(order);
    }
}
