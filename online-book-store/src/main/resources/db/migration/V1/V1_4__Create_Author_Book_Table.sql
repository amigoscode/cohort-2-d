CREATE TABLE author(
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    UNIQUE (first_name, last_name)
);

CREATE TABLE book_author(
    book_id BIGSERIAL REFERENCES book (id) ON DELETE CASCADE,
    author_id BIGSERIAL REFERENCES author (id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, author_id)
);

CREATE TABLE book_category(
    book_id BIGSERIAL REFERENCES book (id)  ON DELETE CASCADE,
    category_id BIGSERIAL REFERENCES category (id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, category_id)
);
