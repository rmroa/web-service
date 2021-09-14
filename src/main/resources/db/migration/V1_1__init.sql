CREATE TABLE IF NOT EXISTS news
(
    id    BIGSERIAL PRIMARY KEY,
    date  TIMESTAMP    NOT NULL,
    title VARCHAR(128) NOT NULL,
    text  TEXT         NOT NULL
);

CREATE TABLE IF NOT EXISTS comments
(
    id        BIGSERIAL PRIMARY KEY,
    date      TIMESTAMP                   NOT NULL,
    text      VARCHAR(1000),
    user_name VARCHAR(128)                NOT NULL,
    news_id   BIGINT REFERENCES news (id) NOT NULL
);
