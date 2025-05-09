DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS requests;
DROP TABLE IF EXISTS comments;

CREATE TABLE IF NOT EXISTS users
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(255)        NOT NULL,
    email VARCHAR(512) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS requests
(
    id           SERIAL PRIMARY KEY,
    description  VARCHAR(512),
    requestor_id BIGINT NOT NULL,
    CONSTRAINT fk_requests_requestor FOREIGN KEY (requestor_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS items
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    description  VARCHAR(512) NOT NULL,
    is_available BOOLEAN      NOT NULL,
    owner_id     BIGINT       NOT NULL,
    request_id   BIGINT,
    CONSTRAINT fk_items_owner FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_items_request FOREIGN KEY (request_id) REFERENCES requests (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS bookings
(
    id         SERIAL PRIMARY KEY,
    start_date TIMESTAMP   NOT NULL,
    end_date   TIMESTAMP   NOT NULL,
    item_id    BIGINT      NOT NULL,
    booker_id  BIGINT      NOT NULL,
    status     VARCHAR(10) NOT NULL,
    CONSTRAINT fk_bookings_item FOREIGN KEY (item_id) REFERENCES items (id) ON DELETE CASCADE,
    CONSTRAINT fk_bookings_booker FOREIGN KEY (booker_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comments
(
    id        SERIAL PRIMARY KEY,
    text      VARCHAR(512),
    item_id   BIGINT    NOT NULL,
    author_id BIGINT    NOT NULL,
    created   TIMESTAMP NOT NULL,
    CONSTRAINT fk_comments_item FOREIGN KEY (item_id) REFERENCES items (id) ON DELETE CASCADE,
    CONSTRAINT fk_comments_author FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE CASCADE
);