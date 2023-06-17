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

INSERT INTO MENU (menu_date, restaurant_id)
VALUES ('2023-06-01', 1),
       ('2023-06-01', 2),
       (CURRENT_DATE(), 1),
       (CURRENT_DATE(), 2);

INSERT INTO DISH (name, price, menu_id)
VALUES ('Dish1', 200, 1),
       ('Dish2', 220, 1),
       ('Dish3', 300, 1),
       ('Dish4', 250, 2),
       ('Dish5', 200, 2),
       ('Dish6', 120, 2),
       ('Dish7', 400, 2),
       ('Dish8', 450, 3),
       ('Dish9', 700, 3),
       ('Dish10', 500, 3),
       ('Dish11', 300, 4),
       ('Dish12', 800, 4);

INSERT INTO VOTE (vote_date, restaurant_id, user_id)
VALUES ('2023-05-01', 1, 1),
       ('2023-05-01', 2, 2),
       (CURRENT_DATE(), 1, 1);