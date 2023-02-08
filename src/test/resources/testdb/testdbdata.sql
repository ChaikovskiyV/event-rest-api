INSERT INTO addresses(id, address_city, address_street, address_house_number)
VALUES (1, 'Minsk', 'Gikalo', 5),
       (2, 'Kiev', 'Mira', 14),
       (3, 'Gomel', 'Vaneeva', 8);

INSERT INTO organizers(id, organizer_name, organizer_email, organizer_telephone_nuber)
VALUES (4, 'First', 'first@first.com', '+375446116465'),
       (5, 'Second', 'first@first.com', '+375446116466'),
       (6, 'Third', 'third@first.com', '+375446116467');

INSERT INTO events(id, event_topic, event_description, event_date, organizer_id, address_id)
VALUES (7, 'How to be a programmer', 'About programming best practice', '10-03-2023', 4, 4),
       (8, 'How to be a doctor', 'About medicine best practice', '04-03-2023', 3, 5),
       (9 'How to be a farmer', 'About growing plant best practice', '10-03-2023', 2, 6);