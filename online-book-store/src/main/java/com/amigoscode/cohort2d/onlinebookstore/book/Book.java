package com.amigoscode.cohort2d.onlinebookstore.book;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
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
    @NotNull
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

}
