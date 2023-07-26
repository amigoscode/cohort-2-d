package com.amigoscode.cohort2d.onlinebookstore.order;

import com.amigoscode.cohort2d.onlinebookstore.book.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class OrderItem {
    @Id
    @SequenceGenerator(
            name = "order_item_id_seq",
            sequenceName = "order_item_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_item_id_seq"
    )
    private Long id;

    private BigDecimal price;

    private int quantity;

    public OrderItem(int quantity, Book book) {
        this.quantity = quantity;
        this.book = book;
        this.price = getItemPrice();
    }

    private BigDecimal getItemPrice() {
        return this.book.getPrice().multiply(BigDecimal.valueOf(this.quantity));
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",  referencedColumnName = "id")
    private Order order;

}
