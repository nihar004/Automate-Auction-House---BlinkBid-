create database auction;
use auction;

create table admin
(
	admin_id int primary key auto_increment,
	admin_name varchar(25) not null, 
	admin_email varchar(25) not null,
	admin_password varchar(15) not null
);
select * from admin;

create table buyer
(
	buyer_id int primary key auto_increment,
	buyer_name varchar(20) not null,
	buyer_email varchar(25) not null,
	buyer_password varchar(15) not null,
	buyer_signup_date date not null
);
select * from buyer;	

create table seller
(
	seller_id int primary key auto_increment,
	seller_name varchar(20) not null,
	seller_email varchar(25) not null,
	seller_password varchar(15) not null,
	seller_signup_date date not null
);
select * from seller;
 
drop table product;
 CREATE TABLE product (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    seller_id INT NOT NULL,
    disputed BOOLEAN NOT NULL,
    product_added_date DATE NOT NULL,
    availability BOOLEAN NOT NULL,
    product_name VARCHAR(25) NOT NULL,
    product_time_limit DATE NOT NULL,
    current_bid INT NOT NULL,
    product_selling_price INT,
    buyer_id INT,
    sold BOOLEAN NOT NULL,
    product_sold_date DATE,  -- New column
    dispute_reason VARCHAR(255),  -- New column
    FOREIGN KEY (seller_id) REFERENCES seller (seller_id)
);
select * from product;	
    
CREATE TABLE bids (
    bid_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    buyer_id INT,
    bid_amount INT,
    bid_date DATE,
    FOREIGN KEY (product_id) REFERENCES Product(product_id),
    FOREIGN KEY (buyer_id) REFERENCES Buyer(buyer_id)
);
select * from bids;


// --- data ----
INSERT INTO admin (admin_name, admin_email, admin_password)
VALUES
('Neil Bohr', 'neil@gmail.com', 'adminpass1'),
('Heisenberg', 'hesi@gmial.com', 'adminpass2'),
('Nolan Adams', 'Nolan@gmail.com', 'adminpass3'),
('Jack Jacobs', 'jack@gmial.com', 'adminpass4');
('admin1','admin1@gmail.com','admin1');

INSERT INTO buyer (buyer_id, buyer_name, buyer_email, buyer_password, buyer_signup_date) VALUES
(1, 'Jos Buttler', 'jos@gmail.com', 'buypass1', '2023-01-15'),
(2, 'Roger Binny', 'roger@gmail.com', 'buypass2', '2023-02-20'),
(3, 'Jimmy Anderson', 'jimmy@gmail.com', 'buypass3', '2023-03-10'),
(4, 'Stuart Broad', 'stuart@gmail.com', 'buypass4', '2023-02-20'),
(5, 'Chris Gayle', 'chris@gmail.com', 'buypass5', '2023-02-20'),
(6,'buyer1','buyer1@gmail.com','buyer1','23-12-2');

INSERT INTO seller (seller_id, seller_name, seller_email, seller_password, seller_signup_date) VALUES
(1, 'Roberto Carlos', 'roberto@gmail.com', 'sellpassX', '2023-04-05'),
(2, 'Luis Figo', 'luisY@gmail.com', 'sellpassY', '2023-05-12'),
(3, 'John Wick', 'john@gmail.com', 'sellpassZ', '2023-06-25'),
(4, 'Carlos Sainz', 'carlos@gmail.com', 'sellpassP', '2023-07-01'),
(5, 'Max Muller', 'Max@gmail.com', 'sellpassQ', '2023-08-05'),
(6,'seller1','seller1@gmail.com','seller1','2023-12-03');
   
INSERT INTO product (product_id, seller_id, disputed, product_added_date, availability, product_name, product_time_limit, current_bid, product_selling_price, buyer_id, sold, product_sold_date, dispute_reason) VALUES
(1,1, false, '2023-11-15', false, 'Dining Table', '2024-01-01', 120, 120, 3, true, '2023-12-07', NULL),
(2,2, false, '2023-11-16', false, 'Writing Desk', '2024-01-07', 180, 180, 2, true, '2023-12-07', NULL),
(3,3, true, '2023-09-07', false, 'Camera Set', '2023-12-28', 100, 100, 1, true, '2023-12-07', 'Product is broken - Camera Set'),
(4,4, true, '2023-10-08', false, 'Signed Art Print', '2024-01-18', 1220, 1220, 4, true, '2023-12-07', 'Product is fake - Signed Art Print'),
(5,6, false, '2023-11-09', false, 'Record Collection', '2024-01-19', 140, 140, 6, true, '2023-12-07', NULL),
(6,6, false, '2023-10-11', false, 'Persian Rug', '2024-01-20', 110, 110, 6, true, '2023-12-07', NULL),
(7,6, true, '2023-10-12', false, 'Hardcover Books', '2023-12-21', 130, 130, 6, true, '2023-12-07', 'Poor Quality - Hardcover Books'),
(8,6, false, '2023-01-13', true, 'Leather Handbag', '2024-01-22', 220, NULL, NULL, false, NULL, NULL),
(9,6, false, '2023-01-14', true, 'Vintage Car', '2024-01-23', 280, NULL, NULL, false, NULL, NULL),
(10,6, false, '2023-01-15', true, 'Electric Guitar', '2024-01-24', 250, NULL, NULL, false, NULL, NULL);


-- Leather Handbag
INSERT INTO bids (product_id, buyer_id, bid_amount, bid_date) 
VALUES
(8, 6, 180, '2023-02-11'),
(8, 2, 210, '2023-02-12'),
(8, 3, 220, '2023-12-07');

-- Vintage Car
INSERT INTO bids (product_id, buyer_id, bid_amount, bid_date) 
VALUES 
(9, 4, 180, '2023-12-01'),
(9, 5, 190, '2023-12-01'),
(9, 6, 280, '2023-12-02');

-- Electric Guitar
INSERT INTO bids (product_id, buyer_id, bid_amount, bid_date) 
VALUES 
(10, 2, 180, '2023-12-01'),
(10, 6, 200, '2023-12-01'),
(10, 4, 250, '2023-12-02');