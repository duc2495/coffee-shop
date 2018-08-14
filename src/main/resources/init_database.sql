-- Create tables --

DROP TABLE IF EXISTS "product_type" CASCADE;
CREATE TABLE "product_type"(
	type_name varchar(20) PRIMARY KEY,
	created_at Date NOT NULL DEFAULT now(),
	updated_at Date NOT NULL DEFAULT now()
);

DROP TABLE IF EXISTS "product" CASCADE;
DROP SEQUENCE IF EXISTS "product_id_seq" CASCADE;

CREATE SEQUENCE product_id_seq;
CREATE TABLE "product"(
	product_id integer PRIMARY KEY DEFAULT NEXTVAL('product_id_seq'),
	product_name varchar(30) NOT NULL,
	product_type varchar(20) REFERENCES "product_type"(type_name),
	price integer NOT NULL CHECK (price > 0),
	image_url varchar(100) NOT NULL,
	description varchar(1000) NOT NULL,
	created_at Date NOT NULL DEFAULT now(),
	updated_at Date NOT NULL DEFAULT now()
);

DROP TABLE IF EXISTS "role" CASCADE;
CREATE TABLE "role"(
	role_name varchar(20) PRIMARY KEY,
	created_at Date NOT NULL DEFAULT now(),
	updated_at Date NOT NULL DEFAULT now()
);

DROP TABLE IF EXISTS "account" CASCADE;
CREATE TABLE "account"(
	username varchar(20) PRIMARY KEY,
	password varchar(100) NOT NULL,
	enabled boolean default false,
	role varchar(20) REFERENCES "role"(role_name),
	created_at timestamp NOT NULL DEFAULT now(),
	updated_at timestamp NOT NULL DEFAULT now()
);

DROP SEQUENCE IF EXISTS "customer_id_seq" CASCADE;

CREATE SEQUENCE customer_id_seq;
DROP TABLE IF EXISTS "customer_info" CASCADE;
CREATE TABLE "customer_info"(
	customer_id integer PRIMARY KEY DEFAULT NEXTVAL('customer_id_seq'),
	full_name varchar(50) NOT NULL,
	address varchar(100) NOT NULL,
	phone_number varchar(20) NOT NULL,
	email varchar(30) NOT NULL,
	token varchar(100) NOT NULL,
	expiry_date timestamp with time zone NOT NULL,
	unique (email),
	unique (token),
	created_at timestamp NOT NULL DEFAULT now(),
	updated_at timestamp NOT NULL DEFAULT now()
);

DROP TABLE IF EXISTS "status" CASCADE;
CREATE TABLE "status"(
	status_name varchar(10) PRIMARY KEY,
	created_at timestamp NOT NULL DEFAULT now(),
	updated_at timestamp NOT NULL DEFAULT now()
);


DROP SEQUENCE IF EXISTS "order_id_seq" CASCADE;

CREATE SEQUENCE order_id_seq;
DROP TABLE IF EXISTS "order" CASCADE;
CREATE TABLE "order"(
	order_id integer PRIMARY KEY DEFAULT NEXTVAL('order_id_seq'),
	net_price integer NOT NULL,
	status varchar(10) REFERENCES "status"(status_name) NOT NULL,
	note varchar(200),
	customer_name varchar(50) NOT NULL,
	customer_phone varchar(20) NOT NULL,
	customer_address varchar(100) NOT NULL,
	created_at timestamp NOT NULL DEFAULT now(),
	updated_at timestamp NOT NULL DEFAULT now()
);



DROP TABLE IF EXISTS "order_product" CASCADE;
CREATE TABLE "order_product"(
	order_id integer REFERENCES "order"(order_id),
	product_id integer REFERENCES "product"(product_id),
	quantity integer NOT NULL CHECK (quantity > 0),
	product_name varchar(30) NOT NULL,
	product_type varchar(20) NOT NULL,
	product_price integer NOT NULL,
	created_at timestamp NOT NULL DEFAULT now(),
	updated_at timestamp NOT NULL DEFAULT now()
);

/* create function and procedure */

-- updated_at trigger function --

CREATE OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS
'BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END;'
LANGUAGE plpgsql;
/* create trigger */

DROP TRIGGER IF EXISTS set_timestamp_product_type ON product_type;
CREATE TRIGGER set_timestamp_product_type
BEFORE UPDATE ON product_type
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

DROP TRIGGER IF EXISTS set_timestamp_product ON product;
CREATE TRIGGER set_timestamp_product
BEFORE UPDATE ON product
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

DROP TRIGGER IF EXISTS set_timestamp_customer ON customer_info;
CREATE TRIGGER set_timestamp_customer
BEFORE UPDATE ON customer_info
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

DROP TRIGGER IF EXISTS set_timestamp_role ON role;
CREATE TRIGGER set_timestamp_role
BEFORE UPDATE ON role
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

DROP TRIGGER IF EXISTS set_timestamp_account ON account;
CREATE TRIGGER set_timestamp_account
BEFORE UPDATE ON account
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

DROP TRIGGER IF EXISTS set_timestamp_oder ON "order";
CREATE TRIGGER set_timestamp_oder
BEFORE UPDATE ON "order"
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

DROP TRIGGER IF EXISTS set_timestamp_order_product ON order_product;
CREATE TRIGGER set_timestamp_order_product
BEFORE UPDATE ON order_product
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

DROP TRIGGER IF EXISTS set_timestamp_status ON status;
CREATE TRIGGER set_timestamp_status
BEFORE UPDATE ON status
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

-- adding data --
INSERT INTO "status"(status_name)
VALUES ('ORDERED'), ('SHIPPING'), ('FINISHED'), ('CANCELED');

INSERT INTO "product_type"(type_name)
VALUES ('PURE_COFFEE'), ('FROM_COFFEE'), ('NON_COFFEE');

INSERT INTO "role"(role_name)
VALUES ('ROLE_ADMIN'), ('ROLE_CUSTOMER');

INSERT INTO "account"(username, password, enabled, role)
VALUES ('admin', '$2a$10$17laWpqOFKYZJWwc1FBd.eYL6uQ62Dfw8U3ElX03nABl1D.L.EBZC', true, 'ROLE_ADMIN');
