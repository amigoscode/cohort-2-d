CREATE TABLE user_obs(
     id BIGSERIAL PRIMARY KEY,
     first_name VARCHAR(50) NOT NULL,
     last_name VARCHAR(50) NOT NULL,
     email VARCHAR(250) NOT NULL UNIQUE,
     password VARCHAR(250) NOT NULL,
     phone_number VARCHAR(25) NOT NULL,
     role VARCHAR(50) NOT NULL
);