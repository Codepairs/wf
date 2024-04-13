DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS incomes CASCADE;
DROP TABLE IF EXISTS expenses CASCADE;



CREATE TABLE IF NOT EXISTS users
(
    id    BIGSERIAL PRIMARY KEY UNIQUE ,
    user_name  VARCHAR(200) NOT NULL ,
    email        VARCHAR(254) NOT NULL ,
    password        VARCHAR(30)  NOT NULL
);
--CREATE sequence clients_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS categories
(
    id BIGSERIAL primary key unique ,
    category_name VARCHAR(30) NOT NULL
)

CREATE TABLE IF NOT EXISTS incomes
(
    id BIGSERIAL primary key unique ,
    comment VARCHAR(30) NOT NULL,
    value decimal(10,2) NOT NULL,
    user_id BIGINT REFERENCES users(id),
    category_id BIGINT REFERENCES categories(id)
    --client--
)

CREATE TABLE IF NOT EXISTS expenses
(
    id BIGSERIAL primary key unique ,
    comment VARCHAR(30) NOT NULL,
    value decimal(10,2) NOT NULL,
    user_id BIGINT REFERENCES users(id),
    category_id BIGINT REFERENCES categories(id)
    --client--
)



INSERT INTO users(user_name, email, password) VALUES
('Vassily Petrov', 'vpetrov@jr.com', '123'),
('Pjotr Vasechkin', 'pvasechkin@jr.com', '345');

INSERT INTO categories(category_name) VALUES
('Food'),
('Clothes'),
('Salary'),
('Bonus'),
('Cashback');


INSERT INTO incomes(comment, value, user_id, category_id) VALUES
('Salary', 1000, 1, 1),
('Bonus', 500, 1, 2),
('Cashback', 1500, 2, 3);

INSERT INTO expenses(comment, value, user_id, category_id) VALUES
('Food', 500, 1, 4),
('Clothes', 500, 1, 5),
('Food', 500, 2, 4);

SELECT * FROM users;