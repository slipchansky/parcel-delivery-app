--liquibase formatted sql
--changeset slipchansky:create-superuser

insert into users(username, email, password) values('super', 'user@mail.com', '$2a$10$ZQJ3Pefv2irbwP8WqD7oV.uG13TUt5WpyA9cNxkJvcOb77VBjQcQy');

insert into user_roles(username, roles) values ('super', 'ROLE_SUPER_ADMIN');
