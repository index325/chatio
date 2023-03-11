CREATE TABLE users (
    id bigserial not null,
    email varchar(250),
    password varchar(500) not null,
    name varchar(250),
    primary key (id)
)