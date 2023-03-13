CREATE TABLE user_messages (
    id bigserial not null,
    from_id varchar(255) not null,
    to_id varchar(255) not null,
    message_body text,
    primary key (id)
);

ALTER TABLE user_messages ADD CONSTRAINT fk_user_from_messages
    FOREIGN KEY(from_id)
        REFERENCES users(id);

ALTER TABLE user_messages ADD CONSTRAINT fk_user_to_messages
    FOREIGN KEY(to_id)
        REFERENCES users(id);