CREATE SEQUENCE IF NOT EXISTS generate_id
START WITH 100000000
INCREMENT BY 1
NO CYCLE;

CREATE TABLE IF NOT EXISTS contact_details
(
    entity_id BIGINT DEFAULT nextval('generate_id') PRIMARY KEY,
    phone_number BIGINT NOT NULL,

    email VARCHAR(256) NOT NULL,
    prefecture VARCHAR(256) NOT NULL,
    city VARCHAR(256) NOT NULL,
    zipcode BIGINT NOT NULL,
    level1 VARCHAR(256) NOT NULL,
    level2 VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS users
(
    entity_id BIGINT DEFAULT nextval('generate_id') PRIMARY KEY,
    first_name VARCHAR(256) NOT NULL,
    last_name VARCHAR(256) NOT NULL,
    username varchar(250) NOT NULL,
    password varchar(250) NOT NULL,
    salt varchar(200) NOT NULL,
    date_of_birth DATE NOT NULL,
    contact_details_id BIGINT  NOT NULL

    FOREIGN KEY (contact_details_id)
    REFERENCES contact_details(entity_id)
    ON DELETE CASCADE
);

