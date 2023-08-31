package com.amigoscode.cohort2d.onlinebookstore.order;

import com.amigoscode.cohort2d.onlinebookstore.address.AddressDtoMapper;
import com.amigoscode.cohort2d.onlinebookstore.address.AddressService;
import com.amigoscode.cohort2d.onlinebookstore.book.BookDTOMapper;
import com.amigoscode.cohort2d.onlinebookstore.book.BookService;
import com.amigoscode.cohort2d.onlinebookstore.user.UserDTOMapper;
import com.amigoscode.cohort2d.onlinebookstore.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderDAO orderDAO;
    private final BookService bookService;
    private final UserService userService;
    private final AddressService addressService;

    public OrderService(OrderDAO orderDAO, BookService bookService, UserService userService, AddressService addressService) {
        this.orderDAO = orderDAO;
        this.bookService = bookService;
        this.userService = userService;
        this.addressService = addressService;
    }


    public Order addOrder(Order order) {
        return orderDAO.addOrder(order);
    }

    public List<OrderDTO> getOrdersByUserId(Long userId) {
        List<Order> all = orderDAO.getOrdersByUserId(userId);
        return OrderDTOMapper.INSTANCE.modelToDTO(all);
    }

    public OrderDTO placeOrder(OrderDTO orderDTO) {

        var user = UserDTOMapper.INSTANCE.dtoToModel(userService.getUserById(orderDTO.user().id()));
        var shippingAddress = AddressDtoMapper.INSTANCE.dtoToModel(addressService.getAddressById(orderDTO.shippingAddress().id()));
        var billingAddress = AddressDtoMapper.INSTANCE.dtoToModel(addressService.getAddressById(orderDTO.billingAddress().id()));

         Order order = new Order(
                 orderDTO.orderDate(),
                 generateOrderTrackingNumber(),
                 OrderStatus.PENDING
         );
         order.setUser(user);
         order.setBillingAddress(billingAddress);
         order.setShippingAddress(shippingAddress);

         orderDTO.orderItems().forEach(orderItemDTO -> {
             var book = BookDTOMapper.INSTANCE.dtoToModel(bookService.getBookById(orderItemDTO.book().id()));
             var orderItem = new OrderItem(orderItemDTO.quantity(), book);
             order.addOrderItem(orderItem);
         });

         order.setTotalAmount(order.getItemsTotalAmount());
         order.setTotalQuantity(order.getItemsTotalQuantiy());

        return OrderDTOMapper.INSTANCE.modelToDTO(orderDAO.addOrder(order));
    }

    private String generateOrderTrackingNumber() {

        // generate a random UUID number (UUID version-4)
        // For details see: https://en.wikipedia.org/wiki/Universally_unique_identifier
        //
        return UUID.randomUUID().toString();
    }
}
