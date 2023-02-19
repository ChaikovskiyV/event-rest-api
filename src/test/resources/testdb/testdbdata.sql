INSERT INTO addresses (id, address_city, address_street, address_house_number)
VALUES (1, 'Minsk', 'Gikalo', 5),
       (2, 'Kiev', 'Mira', 14),
       (3, 'Gomel', 'Vaneeva', 8);

INSERT INTO organizers (id, organizer_name, organizer_email, organizer_telephone_number)
VALUES (4, 'First', 'first@first.com', '+375446116465'),
       (5, 'Second', 'second@second.com', '+375446116466'),
       (6, 'Third', 'third@third.com', '+375446116467');

INSERT INTO events (id, event_topic, event_description, event_date, organizer_id, address_id)
VALUES (7, 'How to be a programmer', 'About programming best practice', '2023-03-10 14:10', 4, 1),
       (8, 'How to be a doctor', 'About medicine best practice', '2023-04-10 15:10', 5, 3),
       (9, 'How to be a farmer', 'About growing plant best practice', '2023-03-10 16:10', 6, 2);