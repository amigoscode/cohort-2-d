package com.amigoscode.cohort2d.onlinebookstore.category;

import com.amigoscode.cohort2d.onlinebookstore.book.Book;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.Objects;

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
    private List<Book> books;

    public Category(Long id, @NotBlank String name, @NotBlank String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(name, category.name) &&
                Objects.equals(description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

}
