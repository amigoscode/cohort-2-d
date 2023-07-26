package com.amigoscode.cohort2d.onlinebookstore.order;

import com.amigoscode.cohort2d.onlinebookstore.address.Address;
import com.amigoscode.cohort2d.onlinebookstore.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Order {
    @Id
    @SequenceGenerator(
            name = "orders_id_seq",
            sequenceName = "orders_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "orders_id_seq"
    )
    private Long id;


    @Column(name = "order_date", nullable = false)
    @NotNull
    private LocalDateTime orderDate;

    @Column(name = "order_tracking_number", nullable = false)
    @NotNull
    private String orderTrackingNumber;

    @Column(name = "total_amount", nullable = false)
    @NotNull
    private BigDecimal totalAmount;

    @Column(name = "total_quantity", nullable = false)
    @NotNull
    private int totalQuantity;

    @Column(name = "order_status", nullable = false, length = 25)
    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderItem> orderItems = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_address_id")
    private Address billingAddress;

    public Order(@NotNull LocalDateTime orderDate, @NotNull String orderTrackingNumber, @NotNull BigDecimal totalAmount, @NotNull int totalQuantity, @NotNull OrderStatus orderStatus, User user, Address shippingAddress, Address billingAddress) {
        this.orderDate = orderDate;
        this.orderTrackingNumber = orderTrackingNumber;
        this.totalAmount = totalAmount;
        this.totalQuantity = totalQuantity;
        this.orderStatus = orderStatus;
        this.user = user;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
    }

    public Order(@NotNull LocalDateTime orderDate, @NotNull String orderTrackingNumber,
                 @NotNull BigDecimal totalAmount, @NotNull int totalQuantity, @NotNull OrderStatus orderStatus) {
        this.orderDate = orderDate;
        this.orderTrackingNumber = orderTrackingNumber;
        this.totalAmount = totalAmount;
        this.totalQuantity = totalQuantity;
        this.orderStatus = orderStatus;
    }

    public Order(@NotNull LocalDateTime orderDate, @NotNull String orderTrackingNumber, @NotNull OrderStatus orderStatus) {
        this.orderDate = orderDate;
        this.orderTrackingNumber = orderTrackingNumber;
        this.orderStatus = orderStatus;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public BigDecimal getItemsTotalAmount(){
        BigDecimal amount = BigDecimal.valueOf(0.0);
        for(OrderItem item: this.orderItems){
            amount = amount.add(item.getPrice());
        }
        return amount;
    }

    public int getItemsTotalQuantiy() {
        int quantity = 0;
        for(OrderItem item: this.orderItems){
            quantity += item.getQuantity();
        }
        return quantity;
    }
}
