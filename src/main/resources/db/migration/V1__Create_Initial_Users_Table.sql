CREATE TABLE PUBLIC.USERS
(
  id VARCHAR(64) NOT NULL,
  icon VARCHAR(64),
  name VARCHAR(64) NOT NULL,
  email VARCHAR(64) NOT NULL
);
ALTER TABLE PUBLIC.USERS
 ADD CONSTRAINT unique_id UNIQUE (id);
ALTER TABLE PUBLIC.USERS
 ADD CONSTRAINT unique_name UNIQUE (name);
ALTER TABLE PUBLIC.USERS
 ADD CONSTRAINT unique_email UNIQUE (email);
