



DROP TABLE clients;
DROP TABLE address;
DROP TABLE phone;

CREATE TABLE clients (
        client_id serial,
        first_name varchar(128),
        last_name varchar(128),
        active boolean
);

CREATE TABLE address (
        address_id serial,
        client_id int,
        street varchar(128),
        city varchar(128),
        state char(2)
);

CREATE TABLE phone (
        phone_id serial,
        client_id int,
        phone_number varchar(20),
        type varchar(20)
);

INSERT INTO clients VALUES (1, 'Jane', 'Doe', true);
INSERT INTO clients VALUES (2, 'Jenny', 'Smith', false);
INSERT INTO clients VALUES (3, 'Dave', 'Warthog', true);
INSERT INTO clients VALUES (4, 'Sasha', 'Red', true);

INSERT INTO address VALUES (DEFAULT, 1, '123 Street', 'Columbus', 'OH');
INSERT INTO address VALUES (DEFAULT, 2, 'ABC Corner Blvd', 'Cleveland', 'OH');
INSERT INTO address VALUES (DEFAULT, 3, 'Yay Lane', 'Columbus', 'OH');
INSERT INTO address VALUES (DEFAULT, 4, 'Bill Road', 'New Town', 'OH');

INSERT INTO phone VALUES (DEFAULT, 1, '614-555-1212', 'WORK');
INSERT INTO phone VALUES (DEFAULT, 1, '740-888-4545', 'HOME');
INSERT INTO phone VALUES (DEFAULT, 2, '614-867-5309', 'MOBILE');
INSERT INTO phone VALUES (DEFAULT, 3, '888-882-4509', 'WORK');
INSERT INTO phone VALUES (DEFAULT, 4, '555-555-5812', 'HOME');
