package com.amigoscode.cohort2d.onlinebookstore.book;


import com.amigoscode.cohort2d.onlinebookstore.author.Author;
import com.amigoscode.cohort2d.onlinebookstore.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @SequenceGenerator(
            name = "book_id_seq",
            sequenceName = "book_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_id_seq"
    )
    private Long id;


    @NotNull
    @Column(unique=true)
    private String isbn;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer numberOfPages;

    @NotNull
    private Integer quantity;

    @NotNull
    @Column(columnDefinition = "LOCALDATE")
    private LocalDate publishDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BookFormat bookFormat;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();


    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER,
                cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();


    public void addAuthor(Author author) {
        this.authors.add(author);
        author.getBooks().add(this);
    }

    public void removeAuthor(Author author) {
        this.authors.remove(author);
        author.getBooks().remove(this);
    }

    public void addCategory(Category category) {
        this.categories.add(category);
        category.getBooks().add(this);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
        category.getBooks().remove(this);
    }
}
