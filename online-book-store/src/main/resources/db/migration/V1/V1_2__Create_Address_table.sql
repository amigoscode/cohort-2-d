CREATE TABLE address(
     id BIGSERIAL PRIMARY KEY,
     street VARCHAR(250) NOT NULL,
     second_line VARCHAR(250),
     city VARCHAR(100) NOT NULL,
     zip_code VARCHAR(50) NOT NULL,
     country VARCHAR(50) NOT NULL
);

