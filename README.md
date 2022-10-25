Event rest api

The api processes http requests from a client api and sends requests to the database to perform CRUD operations with data.
This api was created using Gradle, Java 17, PostgreSQL, Spring Boot and Hibernate.

This api handle follow endpoints:

GET:
- http://host:port/api/v1/events - get all events;\
this request can be complemented by parameters:
  - organizer - search by organizer name;
  - eventDate - search by event date (date has to match format 'HH-mm dd-MM-yyyy');
  - eventTopic - search by event topic;
  - sort - sorting by the organizer name, eventDate or(and) eventTopic fields (example([]-optional): sort=eventDate asc(desc)[, eventTopic asc(desc), organizerName asc(desc)]), by default, data is sorted by eventDate desc.
- http://host:port/api/v1/events/id - get an event by id;

POST:
- http://host:port/api/v1/events - register(create) a new event, a request body should contain json like:\
{"eventTopic":"",\
  "eventDescription":"",\
  "organizer":\
  {\
    "organizerName":"",\
    "organizerEmail":"",\
    "organizerTelephoneNumber":"+xxxxxxxxxxxx"\
  },\
  "eventDate":"HH-mm dd-MM-yyyy",\
  "address":\
  {\
    "addressCity":"",\
    "addressStreet":"",\
    "addressHouseNumber":\
  }\
}

PUT:
- http://host:port/api/v1/events/id - update the event with id,
a request body should contain json with fields we need to update;

 DELETE:
- http://host:port/api/v1/events/id - delete the event with id.


To run this rest api you need:

1) create a database event_base in PostreSQL and run follow script:

CREATE TABlE addresses (
id bigint NOT NULL,
address_city varchar(30),
address_street varchar(30),
address_house_number int,
CONSTRAINT addresses_pkey PRIMARY KEY (id)
);

CREATE TABlE organizers (
id bigint NOT NULL,
organizer_name varchar(30),
organizer_email varchar(50),
organizer_telephone_number varchar(15),
CONSTRAINT organizers_pkey PRIMARY KEY (id)
);

CREATE TABlE events (
id bigint NOT NULL,
event_topic varchar(30),
event_description varchar(250),
event_date timestamp,
organizer_id bigint NOT NULL,
address_id bigint NOT NULL,
CONSTRAINT events_pkey PRIMARY KEY (id),
CONSTRAINT organizer_foreign_key FOREIGN KEY (organizer_id)
REFERENCES organizers (id)
ON UPDATE cascade ON DELETE no action,
CONSTRAINT address_foreign_key FOREIGN KEY (address_id)
REFERENCES addresses (id)
ON UPDATE cascade ON DELETE no action
);

2) pull this project to your repository;
3) change the 'spring.datasource.password' property in the 'src/main/resources/application.properties' file to meaning that suits your database;
4) open terminal, go to the package with this api and run the command ./gradlew bootRun;
5) by default, this api runs on the port 8088, if we need another port, we need to change the property 'server.port' to meaning that fits you.