drop table if exists user_role;
drop table if exists role_permission;
drop table if exists role;
drop table if exists permission;
drop table if exists authorities;
drop table if exists users;

create table if not exists users
(
    id serial primary key,
    username varchar(50) not null,
    password varchar(500) not null,
    enabled tinyint not null default 1,
    created_at datetime  not null default now(),
    last_login datetime
    );
create table if not exists authorities
(
    id serial primary key ,
    user_id bigint unsigned not null,
    authority varchar(50) not null,
    constraint  fk_user_authorities_1
    foreign key (user_id)
    references users (id)
    on delete cascade
    on update cascade
    );

# alter table authorities add constraint  fk_user_authorities_1
    #         foreign key (user_id)
#             references users (id)
#         on delete cascade
#         on update cascade ;

create table if not exists role (
                                    id serial primary key ,
                                    name varchar(256)
    );

create table if not exists user_role (
                                         id serial primary key ,
                                         user_id bigint unsigned not null,
                                         role_id bigint unsigned not null,
                                         constraint ix_user_role
                                         unique index  (user_id, role_id),
    constraint fk_user_role_user_id_01
    foreign key (user_id) references users(id),
    constraint fk_user_role_role_id_01
    foreign key (role_id) references role(id)
    );

create table if not exists permission (
                                          id serial primary key ,
                                          name varchar(256),
    code varchar(256),
    url varchar(128),
    type varchar(32),
    parent_id bigint,
    order_no int,
    icon varchar(128),
    created_at datetime not null default now(),
    updated_at datetime
    );

create table if not exists role_permission (
                                               id serial primary key ,
                                               role_id bigint unsigned not null,
                                               permission_id bigint unsigned not null,
                                               constraint ix_role_permission
                                               unique index  (role_id, permission_id),
    constraint fk_role_permission_role_id_01
    foreign key (role_id) references role(id),
    constraint fk_role_permission_permission_id
    foreign key (permission_id) references permission(id)
    );


insert into users (username, password, enabled)
values
    ('test', '$2a$10$oPGY1Zz5ugbX.Ho.U7wP0uDQtDx3okOVCjscGoQFzYGIYKray2UO.', 1),
    ('user', '$2a$10$RcWaGUzNqtiUlTNsuUspkO0GAVRVD1K/qFPDSIe9LKob8Yd9mln6e', 1),
    ('wsf', '$2a$10$MJ1wm9G1ICbRQ6..8G2u8.EHo0TS9Fe0O4LynnZwRg8wTamS1Qjpi', 1);
