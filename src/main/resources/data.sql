INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@gmail.com', '{noop}password'),
       ('User2', 'user2@gmail.com', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('USER', 2),
       ('ADMIN', 3);

INSERT INTO RESTAURANT (name, address)
VALUES ('Restaurant1', 'Address1'),
       ('Restaurant2', 'Address2');

INSERT INTO MENU (menu_date, price, restaurant_id)
VALUES ('2023-05-01', 500, 1),
       ('2023-05-01', 600, 2),
       (CURRENT_DATE(), 320, 1),
       (CURRENT_DATE(), 300, 2);

INSERT INTO DISH (menu_id, name)
VALUES (1, 'Блюдо1'),
       (1, 'Блюдо2'),
       (1, 'Блюдо3'),
       (2, 'Блюдо4'),
       (2, 'Блюдо5'),
       (3, 'Блюдо6'),
       (3, 'Блюдо7'),
       (3, 'Блюдо8'),
       (3, 'Блюдо9'),
       (4, 'Блюдо10'),
       (4, 'Блюдо11'),
       (4, 'Блюдо12');

INSERT INTO VOTE (vote_date, restaurant_id, user_id)
VALUES ('2023-05-01', 1, 1),
       ('2023-05-01', 2, 2),
       (CURRENT_DATE(), 1, 1);