package com.amigoscode.cohort2d.onlinebookstore.Order;

import com.amigoscode.cohort2d.onlinebookstore.AbstractTestcontainers;
import com.amigoscode.cohort2d.onlinebookstore.address.Address;
import com.amigoscode.cohort2d.onlinebookstore.address.AddressRepository;
import com.amigoscode.cohort2d.onlinebookstore.author.Author;
import com.amigoscode.cohort2d.onlinebookstore.author.AuthorRepository;
import com.amigoscode.cohort2d.onlinebookstore.book.Book;
import com.amigoscode.cohort2d.onlinebookstore.book.BookFormat;
import com.amigoscode.cohort2d.onlinebookstore.book.BookRepository;
import com.amigoscode.cohort2d.onlinebookstore.category.Category;
import com.amigoscode.cohort2d.onlinebookstore.category.CategoryRepository;
import com.amigoscode.cohort2d.onlinebookstore.order.Order;
import com.amigoscode.cohort2d.onlinebookstore.order.OrderItem;
import com.amigoscode.cohort2d.onlinebookstore.order.OrderRepository;
import com.amigoscode.cohort2d.onlinebookstore.order.OrderStatus;
import com.amigoscode.cohort2d.onlinebookstore.user.User;
import com.amigoscode.cohort2d.onlinebookstore.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private OrderRepository underTest;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    void shouldGetOrdersByUserId() {

        // Given
        Long id = 10L;

        Address shippingAddress = addressRepository.save(new Address(null, "1 main street", "", "Houston", "74225", "USA"));

        Address billingAddress = addressRepository.save(new Address(null, "2 main street", "", "Houston", "74225", "USA"));

        User user = userRepository.save(new User(
                "John", "Doe", "jd@gmail.com", "password", "", "user", List.of(shippingAddress, billingAddress), Collections.emptySet()
        ));
        var userId = user.getId();

        Book book1 = bookRepository.save(getBook(1L, "1234567891234"));
        Book book2 = bookRepository.save(getBook(2L, "1234987654321"));

        var orderItem1 = new OrderItem(1, book1);
        var orderItem2 = new OrderItem(2, book2);

        Order order = new Order(LocalDateTime.now(), "123", BigDecimal.valueOf(30.00), 3, OrderStatus.PENDING, user, shippingAddress, billingAddress);
        order.addOrderItem(orderItem1);
        order.addOrderItem(orderItem2);

        underTest.save(order);

        // When
        var actual = underTest.getOrdersByUserId(userId);

        // Then
        assertThat(actual).isNotNull().hasSize(1);
        assertThat(actual.get(0).getUser().getId()).isEqualTo(userId);
    }

    private Book getBook(Long id, String isbn) {
        Author author = authorRepository.save(new Author(1L, "Douglas", "Norman"));
        List<Author> authors = new ArrayList<>();
        authors.add(author);

        Category category = categoryRepository.save(new Category(1L, "Best New Sellers", "Mystery"));
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        Book book = new Book(
                1L,
                isbn,
                "Lord of the Rings",
                "Fantasy book",
                BigDecimal.valueOf(10.00),
                300,
                250,
                LocalDate.of(1954, 7, 29),
                BookFormat.DIGITAL,
                authors,
                categories
        );
        return book;
    }
}
