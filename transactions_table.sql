CREATE TABLE transactions(
id INT auto_increment PRIMARY KEY,
transaction_type VARCHAR(10) NOT NULL,
transaction_description VARCHAR(50) NOT NULL,
amount DECIMAL(10,2) NOT NULL,
transaction_date VARCHAR(10)
);
