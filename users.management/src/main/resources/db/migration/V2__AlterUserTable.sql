alter table users add column status varchar(20) default 'ACTIVE';
alter table users add column id_user_keycloak varchar(255);

alter table users add constraint user_ck_status CHECK (status IN('ACTIVE', 'INACTIVE'));