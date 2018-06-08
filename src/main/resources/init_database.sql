DROP TABLE IF EXISTS "product_type" CASCADE;
CREATE TABLE "product_type"(
	type_name varchar(20) PRIMARY KEY
);

DROP TABLE IF EXISTS "product" CASCADE;
DROP SEQUENCE IF EXISTS "product_id_seq" CASCADE;

CREATE SEQUENCE product_id_seq;
CREATE TABLE "product"(
	product_id integer PRIMARY KEY DEFAULT NEXTVAL('product_id_seq'),
	product_name varchar(30) NOT NULL,
	product_type varchar(20) REFERENCES "product_type"(type_name),
	price integer NOT NULL,
	image_url varchar(100) NOT NULL,
	description varchar(100) NOT NULL
);

DROP TABLE IF EXISTS "role" CASCADE;
CREATE TABLE "role"(
	role_name varchar(10) PRIMARY KEY
);

DROP TABLE IF EXISTS "account" CASCADE;
CREATE TABLE "account"(
	username varchar(20) PRIMARY KEY,
	password varchar(100) NOT NULL,
	enabled boolean default false,
	role varchar(10) REFERENCES "role"(role_name) 
);

DROP TABLE IF EXISTS "user" CASCADE;
CREATE TABLE "user"(
	username varchar(20) PRIMARY KEY REFERENCES "account"(username),
	fullname varchar(50) NOT NULL,
	address varchar(100) NOT NULL,
	phone_number varchar(20) NOT NULL,
	email varchar(30) NOT NULL
);

DROP TABLE IF EXISTS "status" CASCADE;
CREATE TABLE "status"(
	status_name varchar(10) PRIMARY KEY
);

DROP TABLE IF EXISTS "order" CASCADE;
DROP SEQUENCE IF EXISTS "order_id_seq" CASCADE;

CREATE SEQUENCE order_id_seq;
CREATE TABLE "order"(
	order_id integer PRIMARY KEY DEFAULT NEXTVAL('order_id_seq'),
	username varchar(20) REFERENCES "account"(username),
	customer_phone_number varchar(20) NOT NULL,
	customer_address varchar(100) NOT NULL,
	customer_full_name varchar(50) NOT NULL,
	net_price integer NOT NULL,
	created_time date NOT NULL,
	finished_time date,
	status varchar(10) REFERENCES "status"(status_name),
	note varchar(200)
)

