CREATE SEQUENCE IF NOT EXISTS generate_id
START WITH 100000000
INCREMENT BY 1
NO CYCLE;

CREATE TABLE IF NOT EXISTS contact_details
(
    entity_id BIGINT DEFAULT nextval('generate_id') PRIMARY KEY,
    phone_number BIGINT,
    email VARCHAR(256) NOT NULL,
    prefecture VARCHAR(256) NULL,
    city VARCHAR(256) NULL,
    zipcode BIGINT NULL,
    level1 VARCHAR(256) NULL,
    level2 VARCHAR(256) NULL
);

CREATE TABLE IF NOT EXISTS users
(
    entity_id BIGINT DEFAULT nextval('generate_id') PRIMARY KEY,
    first_name VARCHAR(256) NOT NULL,
    last_name VARCHAR(256) NOT NULL,
    username varchar(256) NOT NULL,
    provider varchar(256) NOT NULL,
    password varchar(250) NULL,
    salt varchar(200) NOT NULL,
    date_of_birth DATE NULL,
    contact_details_id BIGINT  NOT NULL,

    FOREIGN KEY (contact_details_id)
    REFERENCES contact_details(entity_id)
    ON DELETE CASCADE
);

