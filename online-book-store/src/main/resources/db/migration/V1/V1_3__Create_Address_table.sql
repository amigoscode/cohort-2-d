CREATE TABLE address(
     id BIGSERIAL PRIMARY KEY,
     street VARCHAR(250) NOT NULL,
     second_line VARCHAR(250),
     city VARCHAR(100) NOT NULL,
     zip_code VARCHAR(50) NOT NULL,
     country VARCHAR(50) NOT NULL,
     user_id BIGINT,

     CONSTRAINT FK_address_user FOREIGN KEY (user_id) REFERENCES "user_obs" ("id")
);

