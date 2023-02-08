DROP SCHEMA testdb IF EXISTS;
DROP TABLE events IF EXISTS;
DROP TABLE organizers IF EXISTS;
DROP TABLE addresses IF EXISTS;
DROP SEQUENCE HIBERNATE_SEQUENCE IF EXISTS;

CREATE SCHEMA testdb;

CREATE TABLE addresses
(
    id  int8 NOT NULL,
    address_city varchar(255) NOT NULL,
    address_street varchar(255) NOT NULL,
    address_house_number int8 NOT NULL,
    CONSTRAINT address_pkey PRIMARY KEY (id)
);

CREATE TABLE organizers
(
    id  int8 NOT NULL,
    organizer_name varchar(255) NOT NULL,
    organizer_email varchar(255) NOT NULL,
    organizer_telephone_number varchar(255) NOT NULL,
    CONSTRAINT organizer_pkey PRIMARY KEY (id)
);

CREATE TABLE events
(
    id  int8 NOT NULL,
    event_topic varchar(255) NOT NULL,
    event_description varchar(255) NOT NULL,
    event_date date NOT NULL,
    CONSTRAINT event_pkey PRIMARY KEY (id)
);

ALTER TABLE events
    ADD CONSTRAINT address_fk FOREIGN KEY (address_id) REFERENCES public.address (id);
ALTER TABLE events
    ADD CONSTRAINT organizer_fk FOREIGN KEY (organizer_id) REFERENCES public.organizer (id);

CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 1 INCREMENT BY 1;