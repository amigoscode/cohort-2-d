package com.amigoscode.cohort2d.onlinebookstore;

import com.amigoscode.cohort2d.onlinebookstore.address.AddressDao;
import com.amigoscode.cohort2d.onlinebookstore.book.Book;
import com.amigoscode.cohort2d.onlinebookstore.book.BookDAO;
import com.amigoscode.cohort2d.onlinebookstore.order.Order;
import com.amigoscode.cohort2d.onlinebookstore.order.OrderDAO;
import com.amigoscode.cohort2d.onlinebookstore.order.OrderItem;
import com.amigoscode.cohort2d.onlinebookstore.user.UserDao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootApplication
public class OnlineBookStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineBookStoreApplication.class, args);
	}
}
