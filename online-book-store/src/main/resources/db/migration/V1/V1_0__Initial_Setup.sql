CREATE TABLE category(
     id BIGSERIAL PRIMARY KEY,
     name TEXT NOT NULL UNIQUE,
     description TEXT NOT NULL
);