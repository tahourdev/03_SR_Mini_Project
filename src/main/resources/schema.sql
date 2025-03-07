CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          unit_price DECIMAL(10, 2) NOT NULL,
                          quantity INT NOT NULL,
                          imported_date DATE DEFAULT NOW()
);

insert into products (name, unit_price, quantity) values
('Gaming Monitor', 199.99, 20),
('External Hard Drive', 79.99, 35),
('Smartphone Stand', 12.50, 60),
('Portable Power Bank', 45.99, 50),
('Wireless Earbuds', 69.99, 40),
('Ergonomic Office Chair', 149.99, 15),
('4K Webcam', 89.99, 25),
('Mechanical Gaming Mouse', 39.99, 45),
('Smart LED Desk Lamp', 34.99, 30),
('Laptop Cooling Pad', 29.99, 50);


select * from products;