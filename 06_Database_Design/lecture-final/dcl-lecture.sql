
-- Create a new User with a Password
CREATE USER rachelle WITH PASSWORD '123';

-- Grant SELECT ON a table to a user
GRANT SELECT ON contact TO rachelle;


-- Grant INSERT ON a table to a user
GRANT INSERT ON contact TO rachelle;

-- Grant SELECT ON a sequence to a user
GRANT USAGE, SELECT ON SEQUENCE contact_contact_id_seq TO rachelle;

-- Revoke SELECT ON a table from a user
REVOKE SELECT ON contact FROM rachelle;


-- GRANT means to give a user access to something
-- REVOKE means to take away user access from something          
