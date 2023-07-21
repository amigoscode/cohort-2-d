package com.amigoscode.cohort2d.onlinebookstore.author;

import com.amigoscode.cohort2d.onlinebookstore.book.Book;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Author {

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
    private String firstName;

    @NotNull
    private String lastName;

    @EqualsAndHashCode.Exclude
    @ManyToMany(
            mappedBy = "authors",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Book> books = new HashSet<>();


    public Author(Long id, @NotNull String firstName, @NotNull String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
