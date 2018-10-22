DELETE FROM user_meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

--select * from users;

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO user_meals (user_id, calories, description, dateTime) VALUES
  (100001, 500,  'Админ завтрак', '2018-01-01 09:00:10'),
  (100001, 1000, 'Админ обед',    '2018-01-02 12:00:20'),
  (100001, 1500, 'Админ ужин',    '2018-01-03 18:00:30'),
  (100000, 501,  'Юзер ланч',     '2018-01-04 09:00:40'),
  (100000, 1001, 'Юзер обед',     '2018-01-05 12:00:50'),
  (100000, 1501, 'Юзер ужин',     '2018-01-06 18:01:00')
;

select * from user_meals;

--SELECT * FROM user_meals WHERE user_id=100000 ORDER BY dateTime DESC