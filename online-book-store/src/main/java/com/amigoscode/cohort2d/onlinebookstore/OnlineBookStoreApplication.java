package com.amigoscode.cohort2d.onlinebookstore;

import com.amigoscode.cohort2d.onlinebookstore.book.BookRepository;

import com.amigoscode.cohort2d.onlinebookstore.book.BookDemoData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineBookStoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner createBookDemoData(BookRepository bookRepository, BookDemoData bookDemoData) {
		return args -> {
			if (bookRepository.count() == 0) {
				bookRepository.saveAll(bookDemoData.generateData());
			}
		};
	}

	@Bean
	public BookDemoData bookDemoData() {
		return new BookDemoData();
	}


}
