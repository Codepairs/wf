CREATE TABLE IF NOT EXISTS users
(
    id      BIGSERIAL PRIMARY KEY UNIQUE ,
    user_name    VARCHAR(200) NOT NULL ,
    email        VARCHAR(254) NOT NULL ,
    password        VARCHAR(30)  NOT NULL
);
--CREATE sequence clients_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS incomes
(
    id       BIGSERIAL primary key unique ,
    comment  VARCHAR(30) NOT NULL,
    value           DECIMAL(10,2) NOT NULL,
    user_id         BIGINT REFERENCES users(user_id)
        --client--
)

INSERT INTO users(user_name, email, password) VALUES
('Vassily Petrov', 'vpetrov@jr.com','123'),
('Pjotr Vasechkin', 'pvasechkin@jr.com', '345');

SELECT * FROM users;