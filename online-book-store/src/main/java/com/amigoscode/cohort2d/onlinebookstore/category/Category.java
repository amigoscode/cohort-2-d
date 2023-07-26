package com.amigoscode.cohort2d.onlinebookstore.category;

import com.amigoscode.cohort2d.onlinebookstore.book.Book;
import com.amigoscode.cohort2d.onlinebookstore.service.EntityIdentifiers;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category implements EntityIdentifiers {

    @Id
    @SequenceGenerator(
            name = "category_id_seq",
            sequenceName = "category_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_id_seq"
    )
    private Long id;

    @NotNull
    @Column(unique=true)
    private String name;

    @NotNull
    private String description;

    @EqualsAndHashCode.Exclude
    @ManyToMany(
            mappedBy = "categories",
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.DETACH
            }

    )
    private Set<Book> books = new HashSet<>();

    public Category(Long id, @NotNull String name, @NotNull String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.getCategories().add(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.getCategories().remove(this);
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getEntityName() {
        return this.getClass().getSimpleName().replace("DTO", "");
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
