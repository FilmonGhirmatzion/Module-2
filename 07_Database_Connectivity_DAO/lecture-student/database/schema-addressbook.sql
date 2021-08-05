DROP TABLE IF EXISTS contact_address;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS contact_phone;
DROP TABLE IF EXISTS phone;
DROP TABLE IF EXISTS email;
DROP TABLE IF EXISTS contact;



CREATE TABLE contact (
        contact_id serial primary key,
        first_name varchar(100) not null,
        last_name varchar(100) not null,
        date_added TIMESTAMP
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

CREATE TABLE email (
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
  
  
CREATE TABLE phone (
        phone_id serial primary key,
        phone_number varchar(32) not null,
        type varchar(23) not null,
        type_description varchar(255),
        
        constraint chk_phone_type check (type IN ('Home', 'Work', 'Mobile', 'Other')),
        constraint chk_phonetypedesc check ( (type = 'Other' AND type_description IS NOT NULL) OR
                (type != 'Other' AND type_description IS NULL ) )
        
);

CREATE TABLE contact_phone (
        contact_id int not null,
        phone_id int not null,
        
        constraint pk_contact_phone primary key (contact_id, phone_id),
        constraint fk_contact_phone_contact_id foreign key (contact_id) references contact(contact_id),
        constraint fk_contact_phone_phone_id foreign key (phone_id) references phone(phone_id)
);

-- These INSERTs use the CURRVAL(sequence) function to get the last value used for the sequence
-- without incrementing it.  This allows for the entries to be added using the sequence so it is current
-- the next time it is used.   WARNING: This only works if the order of the INSERTS is maintained.  If the order is 
-- changed, then it will associate the incorrect ids.  CAUTION: This is 1 ways of using sequences in a schema script, but 
-- should only be used when we are sure there are no other users connected to a database that may be INSERTING other records,
-- which may cause CURRVAL() to return their use of the sequence instead of ours.  In this script is only safe to use, because
-- the earlier portion of the script drops and creates the database when run.  


-- *****************************
-- INSERT Dave Warthog
-- *****************************
INSERT INTO contact (contact_id, first_name, last_name, date_added) 
        VALUES (DEFAULT, 'Dave', 'Warthog', current_timestamp);
-- INSERT Mr Warthog's Home Address
INSERT INTO address (address_id, line_one, line_two, city, postal_code, district, country, type)
        VALUES (DEFAULT, '1245 High Street', 'Apt 51', 'Columbus', '43201', 'OH', 'USA', 'Home');
-- INSERT the contact_address join 
INSERT INTO contact_address VALUES ( CURRVAL('contact_contact_id_seq'), CURRVAL('address_address_id_seq') );
-- INSERT Mr. Warthog's Phone
INSERT INTO phone ( phone_id,phone_number, type)
        VALUES ( DEFAULT, '614-555-1212', 'Mobile');
-- INSERT the contact_phone join
INSERT INTO contact_phone VAlUES ( CURRVAL('contact_contact_id_seq'), CURRVAL('phone_phone_id_seq') );
-- INSERT Mr. Warthog's Email
INSERT INTO email (email_id, contact_id, email_address, type) 
        VALUES ( DEFAULT, CURRVAL('contact_contact_id_seq'), 'warthog@warthogrules.com', 'Personal');
        

-- *****************************
-- INSERT Ratboy Jones
-- *****************************      
INSERT INTO contact (contact_id, first_name, last_name, date_added)
        VALUES (DEFAULT, 'Ratboy', 'Jones', current_timestamp);   
-- INSERT The Rat's Home Address
INSERT INTO address (address_id, line_one, line_two, city, postal_code, district, country, type)
        VALUES (DEFAULT, 'Under the front street bridge', null, 'Columbus', '43201', 'OH', 'USA', 'Home');   
-- INSERT the contact_address join 
INSERT INTO contact_address VALUES ( CURRVAL('contact_contact_id_seq'), CURRVAL('address_address_id_seq') );   
-- INSERT The Rat's Vacation Home Address
INSERT INTO address (address_id, line_one, line_two, city, postal_code, district, country, type, type_description)
        VALUES (DEFAULT, 'OSU Tunnels', 'North Oval Entrance', 'Columbus', '43201', 'OH', 'USA', 'Other', 'Relaxin Spot');   
-- INSERT the contact_address join 
INSERT INTO contact_address VALUES ( CURRVAL('contact_contact_id_seq'), CURRVAL('address_address_id_seq') );     
-- INSERT The Rat's Mobile Phone
INSERT INTO phone ( phone_id,phone_number, type)
        VALUES ( DEFAULT, '614-555-7892', 'Mobile');  
-- INSERT the contact_phone join
INSERT INTO contact_phone VAlUES ( CURRVAL('contact_contact_id_seq'), CURRVAL('phone_phone_id_seq') );      
-- INSERT The Rat's Other Phone
INSERT INTO phone ( phone_id,phone_number, type, type_description)
        VALUES ( DEFAULT, '614-555-7892', 'Other', 'Payphone - ring twice then call back');
-- INSERT the contact_phone joing
INSERT INTO contact_phone VAlUES ( CURRVAL('contact_contact_id_seq'), CURRVAL('phone_phone_id_seq') );     
-- INSERT The Rat's Email
INSERT INTO email (email_id, contact_id, email_address, type) 
        VALUES ( DEFAULT, CURRVAL('contact_contact_id_seq'), 'ratboy@aol.com', 'Personal');
        
-- *****************************
-- INSERT Tall Bobby
-- *****************************
INSERT INTO contact (contact_id, first_name, last_name)
        VALUES (DEFAULT, 'Tall', 'Bobby');
-- INSERT Tall Bobby's Address
INSERT INTO address (address_id, line_one, line_two, city, postal_code, district, country, type)
        VALUES (DEFAULT, '52A 5th Ave', null, 'Columbus', '43220', 'OH', 'USA', 'Work');   
-- INSERT the contact_address join 
INSERT INTO contact_address VALUES ( CURRVAL('contact_contact_id_seq'), CURRVAL('address_address_id_seq') );  
-- INSERT Tall Bobby's Home Phone
INSERT INTO phone ( phone_id,phone_number, type)
        VALUES ( DEFAULT, '614-555-5782', 'Home');  
-- INSERT the contact_phone join
INSERT INTO contact_phone VAlUES ( CURRVAL('contact_contact_id_seq'), CURRVAL('phone_phone_id_seq') );
-- INSERT Tall Bobby's Email
INSERT INTO email (email_id, contact_id, email_address, type) 
        VALUES ( DEFAULT, CURRVAL('contact_contact_id_seq'), 'tallguy@bobbyrocks.com', 'Work');  
        
-- *****************************
-- INSERT Jenny Jones
-- ***************************** 
INSERT INTO contact (contact_id, first_name, last_name, date_added)
        VALUES (DEFAULT, 'Jenny', 'Jones', current_timestamp);
-- INSERT Jenny's Address
INSERT INTO address (address_id, line_one, line_two, city, postal_code, district, country, type)
        VALUES (DEFAULT, '18 Main St', null, 'Newcomerstown', '43832', 'OH', 'USA', 'Home');   
-- INSERT the contact_address join 
INSERT INTO contact_address VALUES ( CURRVAL('contact_contact_id_seq'), CURRVAL('address_address_id_seq') );  
-- INSERT Jenny's Mobile Phone
INSERT INTO phone ( phone_id,phone_number, type)
        VALUES ( DEFAULT, '212-867-5309', 'Mobile');  
-- INSERT the contact_phone join
INSERT INTO contact_phone VAlUES ( CURRVAL('contact_contact_id_seq'), CURRVAL('phone_phone_id_seq') );
-- INSERT Jenny's Home Phone
INSERT INTO phone ( phone_id,phone_number, type)
        VALUES ( DEFAULT, '740-867-5309', 'Home');  
-- INSERT the contact_phone join
INSERT INTO contact_phone VAlUES ( CURRVAL('contact_contact_id_seq'), CURRVAL('phone_phone_id_seq') );
-- INSERT Jenny's Other Phone
INSERT INTO phone ( phone_id,phone_number, type, type_description)
        VALUES ( DEFAULT, '614-867-5309', 'Other', 'Fax');  
-- INSERT the contact_phone join
INSERT INTO contact_phone VAlUES ( CURRVAL('contact_contact_id_seq'), CURRVAL('phone_phone_id_seq') );
-- INSERT Jenny's Email
INSERT INTO email (email_id, contact_id, email_address, type) 
        VALUES ( DEFAULT, CURRVAL('contact_contact_id_seq'), 'jenny@tutone.org', 'Work');  
-- INSERT Jenny's Other Email
INSERT INTO email (email_id, contact_id, email_address, type, type_description) 
        VALUES ( DEFAULT, CURRVAL('contact_contact_id_seq'), 'jenny@stopcalling.io', 'Other', 'Private'); 


-- *****************************
-- INSERT Jimmy Crooked
-- ***************************** 
INSERT INTO contact (contact_id, first_name, last_name, date_added)
        VALUES (DEFAULT, 'Jimmy', 'Crooked', current_timestamp);
-- INSERT Crooked's Billing Address
INSERT INTO address (address_id, line_one, line_two, city, postal_code, district, country, type)
        VALUES (DEFAULT, '1782 South Ave', null, 'Pittsburgh', '15106', 'PA', 'USA', 'Billing');   
-- INSERT the contact_address join 
INSERT INTO contact_address VALUES ( CURRVAL('contact_contact_id_seq'), CURRVAL('address_address_id_seq') );  
-- INSERT Crooked's Shipping Address
INSERT INTO address (address_id, line_one, line_two, city, postal_code, district, country, type)
        VALUES (DEFAULT, 'Box 502', null, 'Pittsburgh', '15106', 'PA', 'USA', 'Shipping');   
-- INSERT the contact_address join 
INSERT INTO contact_address VALUES ( CURRVAL('contact_contact_id_seq'), CURRVAL('address_address_id_seq') );  
-- INSERT Crooked's Work Address
INSERT INTO address (address_id, line_one, line_two, city, postal_code, district, country, type)
        VALUES (DEFAULT, '7205 1st Avenue', 'Suite 4', 'Pittsburgh', '15106', 'PA', 'USA', 'Work');   
-- INSERT the contact_address join 
INSERT INTO contact_address VALUES ( CURRVAL('contact_contact_id_seq'), CURRVAL('address_address_id_seq') );  
-- INSERT Crooked's Email
INSERT INTO email (email_id, contact_id, email_address, type) 
        VALUES ( DEFAULT, CURRVAL('contact_contact_id_seq'), 'jimmycrooked@cobras.com', 'Personal'); 
        

-- *****************************
-- INSERT Jung Choi
-- ***************************** 
INSERT INTO contact (contact_id, first_name, last_name, date_added)
        VALUES (DEFAULT, 'Jung', 'Choi', current_timestamp);
-- INSERT Jung Choi's Address
INSERT INTO address (address_id, line_one, line_two, city, postal_code, district, country, type)
        VALUES (DEFAULT, '85-1 Chebu-dong', null, 'Seoul', '100-093', 'Jongro-gu', 'KOR', 'Work'); 
-- INSERT the contact_address join 
INSERT INTO contact_address VALUES ( CURRVAL('contact_contact_id_seq'), CURRVAL('address_address_id_seq') );  
-- INSERT Jung Choi's Phone
INSERT INTO phone ( phone_id,phone_number, type)
        VALUES ( DEFAULT, '+82-2-312-3456', 'Work');  
-- INSERT the contact_phone join
INSERT INTO contact_phone VAlUES ( CURRVAL('contact_contact_id_seq'), CURRVAL('phone_phone_id_seq') );


-- *****************************
-- INSERT Shelly Sue Samson
-- ***************************** 
INSERT INTO contact (contact_id, first_name, last_name)
        VALUES (DEFAULT, 'Shelly Sue', 'Samson');