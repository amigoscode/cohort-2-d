CREATE TABLE orders(
    id BIGSERIAL PRIMARY KEY,
    order_date timestamp NOT NULL,
    order_tracking_number VARCHAR NOT NULL,
    total_amount DECIMAL(6,2) NOT NULL,
    total_quantity INTEGER NOT NULL,
    order_status VARCHAR(25) NOT NULL,
    user_id BIGINT,
    shipping_address_id BIGINT,
    billing_address_id BIGINT,

    CONSTRAINT FK_order_user FOREIGN KEY (user_id) REFERENCES "user_obs" ("id"),
    CONSTRAINT FK_order_shipping_address FOREIGN KEY (shipping_address_id) REFERENCES "address" ("id"),
    CONSTRAINT FK_order_billing_address FOREIGN KEY (billing_address_id) REFERENCES "address" ("id")
);

CREATE TABLE order_item(
    id BIGSERIAL PRIMARY KEY,
    price DECIMAL(6,2) NOT NULL,
    quantity INTEGER NOT NULL,
    book_id BIGINT,
    order_id BIGINT,

    CONSTRAINT FK_order_item_book FOREIGN KEY (book_id) REFERENCES "book" ("id"),
    CONSTRAINT FK_order_item_order FOREIGN KEY (order_id) REFERENCES "orders" ("id")
);