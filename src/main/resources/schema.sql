CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          unit_price DECIMAL(10, 2) NOT NULL,
                          quantity INT NOT NULL,
                          imported_date DATE NOT NULL
);