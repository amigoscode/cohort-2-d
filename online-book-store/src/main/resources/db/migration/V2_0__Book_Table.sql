CREATE TABLE book(
     id BIGSERIAL PRIMARY KEY,
     isbn VARCHAR(13) UNIQUE,
     title VARCHAR(50) NOT NULL,
     description VARCHAR(250) NOT NULL,
     price DECIMAL(6,2) NOT NULL,
     number_of_pages INTEGER NOT NULL,
     quantity INTEGER NOT NULL,
     book_format VARCHAR(10) NOT NULL,
     publish_date DATE NOT NULL
);