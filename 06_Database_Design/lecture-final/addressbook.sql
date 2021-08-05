-- CREATE DATABASE addressbook;

DROP TABLE IF EXISTS contact_address;
DROP TABLE IF EXISTS email_address;
DROP TABLE IF EXISTS contact;
DROP TABLE IF EXISTS address;


CREATE TABLE contact (
        contact_id serial primary key,
        first_name varchar(100) not null,
        last_name varchar(100) not null
);

CREATE TABLE address (
        address_id serial primary key,
        line_one varchar(64) not null,
        line_two varchar(64),
        postal_code varchar(100) not null,
        city varchar(100) not null,
        district varchar(64) not null,
        country char(3) not null,
        type varchar(23) not null,
        type_description varchar(255),
        
        constraint chk_addresstype check (type IN ('Home', 'Work', 'Shipping', 'Billing', 'Other')),
        constraint chk_addresstypedesc check ( (type = 'Other' AND type_description IS NOT NULL) 
                OR (type != 'Other' AND type_description IS NULL) )
        
);

CREATE TABLE contact_address ( 
        contact_id int not null,
        address_id int not null,
        
        constraint fk_contact_address_contact_id foreign key (contact_id) references contact(contact_id),
        constraint fk_contact_address_address_id foreign key (address_id) references address(address_id),
        constraint pk_contact_address primary key (contact_id, address_id)
);
-- foreign key (column_on_this_table) references other_table(column_on_other_table)

CREATE TABLE email_address (
        email_id serial primary key,
        contact_id int not null,
        email_address varchar(255) not null,
        type varchar(23) not null,
        type_description varchar(255),
        
        constraint fk_email_address_contact_id foreign key (contact_id) references contact(contact_id),
        constraint chk_email_type check (type IN ('Work', 'Personal', 'Other')),
        constraint chk_emailtypedesc check ( (type = 'Other' AND type_description IS NOT NULL) OR
                (type != 'Other' AND type_description IS NULL ) )
);
  
  
INSERT INTO contact (first_name, last_name) VALUES ('John', 'Fulton');
INSERT INTO contact (first_name, last_name) VALUES ('John', 'Jones');
INSERT INTO contact (first_name, last_name) VALUES ('Sally', 'Smith');
INSERT INTO contact (first_name, last_name) VALUES ('Dave', 'Davies');
