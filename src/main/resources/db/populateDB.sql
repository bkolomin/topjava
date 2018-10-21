DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO user_meals (user_id, calories, description, dateTime) VALUES
  (100000, 500,  'Админ завтрак',  '2018-01-01 09:00:10'),
  (100000, 1000, 'Админ обед',  '2018-01-01 12:00:20'),
  (100000, 1500, 'Админ ужин',  '2018-01-01 18:00:30'),
  (100001, 501,  'Юзер ланч',   '2018-01-01 09:00:40'),
  (100001, 1001, 'Юзер обед',   '2018-01-01 12:00:50'),
  (100001, 1501, 'Юзер ужин',   '2018-01-01 18:00:60')
;

-- select * from user_meals;