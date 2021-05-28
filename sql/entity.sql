create table post (
   id bigint not null auto_increment,
    author varchar(255),
    content TEXT not null,
    title varchar(500) not null,
    primary key (id)
) engine=MyISAM

create table user (
   id bigint not null auto_increment,
    email varchar(255),
    name varchar(255),
    picture varchar(255),
    platform varchar(255),
    role varchar(255) not null,
    site_id varchar(255),
    primary key (id)
) engine=MyISAM