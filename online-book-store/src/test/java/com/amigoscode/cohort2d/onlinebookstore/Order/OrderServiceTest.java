package com.amigoscode.cohort2d.onlinebookstore.Order;

import com.amigoscode.cohort2d.onlinebookstore.address.*;
import com.amigoscode.cohort2d.onlinebookstore.author.Author;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorDAO;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorRepository;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorService;
import com.amigoscode.cohort2d.onlinebookstore.book.*;
import com.amigoscode.cohort2d.onlinebookstore.category.Category;
import com.amigoscode.cohort2d.onlinebookstore.category.CategoryRepository;
import com.amigoscode.cohort2d.onlinebookstore.category.CategoryService;
import com.amigoscode.cohort2d.onlinebookstore.order.*;
import com.amigoscode.cohort2d.onlinebookstore.user.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    OrderService underTest;

    @Mock
    private OrderDAO orderDAO;

    @Mock
    private UserService userService;

    @Mock
    private BookService bookService;

    @Mock
    private AddressService addressService;

    @Test
    void shouldGetOrdersByUserId() {
        var id = 5L;
        Order order = new Order();

        when(orderDAO.getOrdersByUserId(id)).thenReturn(List.of(order));

        List<OrderDTO> expected = OrderDTOMapper.INSTANCE.modelToDTO(List.of(order));

        var actual = underTest.getOrdersByUserId(id);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldPlaceAnOrder() {

        var shippingAddress = getAddress(1L);
        var billingAddress = getAddress(2L);
        when(addressService.getAddressById(1L)).thenReturn(shippingAddress);
        when(addressService.getAddressById(2L)).thenReturn(billingAddress);

        var userId = 3L;
        var userDto = UserDTOMapper.INSTANCE.modelToDTO(new User(
                userId,
                "John",
                "Doe",
                "jd@gmail.com",
                "password",
                "",
                "user",
                List.of(AddressDtoMapper.INSTANCE.dtoToModel(shippingAddress), AddressDtoMapper.INSTANCE.dtoToModel(billingAddress)),
                Collections.emptySet()
        ));

        when(userService.getUserById(userId)).thenReturn(userDto);


        var bookId1 = 1L;
        var bookId2 = 2L;
        var bookDto1 = getBook(bookId1, "1234567891234");
        var bookDto2 = getBook(bookId2, "1234987654321");
        when(bookService.getBookById(bookId1)).thenReturn(bookDto1);
        when(bookService.getBookById(bookId2)).thenReturn(bookDto2);

        var orderItem1 = new OrderItemDTO(1L, null, 2, bookDto1);
        var orderItem2 = new OrderItemDTO(2L, null, 1, bookDto2);

        OrderDTO order = new OrderDTO(null,
                LocalDateTime.now(),
                "5af483b7-5b76-455b-8535-ed6f494b280c",
                BigDecimal.valueOf(30.00),
                3,
                OrderStatus.PENDING,
                Set.of(orderItem1, orderItem2),
                userDto,
                shippingAddress,
                billingAddress
        );

        underTest.placeOrder(order);

        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderDAO).addOrder(orderArgumentCaptor.capture());

        Order capturedOrder = orderArgumentCaptor.getValue();
        assertThat(capturedOrder.getId()).isNull();
        assertThat(capturedOrder.getOrderDate()).isEqualTo(order.orderDate());
        assertThat(capturedOrder.getOrderItems().size()).isEqualTo(order.orderItems().size());
    }

    private AddressDto getAddress(Long id) {
        return new AddressDto(id, "1 main street", "", "Houston", "74225", "USA");
    }

    private BookDTO getBook(Long id, String isbn) {
        Author author = new Author();
        author.setId(3L);
        List<Author> authors = List.of(author);

        Category category = new Category();
        category.setId(3L);
        List<Category> categories = List.of(category);

        Book book = new Book(
                id,
                isbn,
                "Lord of the Rings",
                "Fantasy book",
                BigDecimal.valueOf(10.00),
                300,
                50,
                LocalDate.of(1954, 7, 29),
                BookFormat.PHYSICAL,
                authors,
                categories
        );
        return BookDTOMapper.INSTANCE.modelToDTO(book);
    }
}
