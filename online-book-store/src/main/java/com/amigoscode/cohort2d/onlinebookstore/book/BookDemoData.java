package com.amigoscode.cohort2d.onlinebookstore.book;

import com.amigoscode.cohort2d.onlinebookstore.author.Author;
import com.amigoscode.cohort2d.onlinebookstore.category.Category;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookDemoData {

    private final Faker faker = new Faker();

    public List<Book> generateData(){
        int count = 20;

        List<Book> books = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            Book book = new Book();

            book.setTitle(faker.book().title());
            book.setIsbn(faker.code().isbn13());
            book.setDescription(faker.book().publisher());
            book.setPrice(new BigDecimal("19.99"));
            book.setNumberOfPages(faker.number().numberBetween(10, 1000));
            book.setQuantity(faker.number().numberBetween(1, 250));

            Date date = faker.date().birthday();
            book.setPublishDate(date.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate());

            // Alternate between Digital and physical formats when creating demo book entries
            if (i % 2 == 0) {
                book.setBookFormat(BookFormat.DIGITAL);
            } else {
                book.setBookFormat(BookFormat.PHYSICAL);
            }

            books.add(book);
        }
        return books;
    }

}
