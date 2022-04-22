--liquibase formatted sql
--changeset slipchansky:create-tables


CREATE TABLE if not exists users
(
    username character varying(255)  NOT NULL,
    email character varying(255) ,
    password character varying(255) ,
    CONSTRAINT users_pkey PRIMARY KEY (username),
    CONSTRAINT users_email UNIQUE (email)
);

CREATE TABLE if not exists user_roles(
    username character varying(255) NOT NULL,
    roles character varying(255),
    CONSTRAINT user_roles_pkey PRIMARY KEY (username, roles),
    CONSTRAINT fk_role_2_user FOREIGN KEY (username)
        REFERENCES public.users (username) 
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);


