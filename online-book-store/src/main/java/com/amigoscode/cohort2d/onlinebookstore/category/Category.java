package com.amigoscode.cohort2d.onlinebookstore.category;

import com.amigoscode.cohort2d.onlinebookstore.book.Book;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

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

    @NotBlank
    @Column(unique=true)
    private String name;

    @NotBlank
    private String description;

    @EqualsAndHashCode.Exclude
    @ManyToMany(
            mappedBy = "categories"
    )
    private Set<Book> books = new HashSet<>();

    public Category(Long id, @NotBlank String name, @NotBlank String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
