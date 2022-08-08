DROP TABLE IF EXISTS post;

create table post (
    post_id bigint not null AUTO_INCREMENT,
    title varchar(255) not null,
    writer varchar(255) not null,
    password varchar(255) not null,
    content longtext not null,
    created timestamp,
    modified timestamp,
    viewed integer,
    likes integer,
    isNew bit,
    primary key (post_id)
);

DROP TABLE IF EXISTS comment;

create table comment (
    comment_id bigint not null AUTO_INCREMENT,
    writer varchar(255) not null,
    password varchar(255) not null,
    content longtext not null,
    created timestamp,
    modified timestamp,
    likes integer,
    post_id bigint not null,
    primary key (comment_id),
    FOREIGN key (post_id) REFERENCES post (post_id)
);

DROP TABLE IF EXISTS hashtag;

create table hashtag (
    hashtag_id bigint not null AUTO_INCREMENT,
    name varchar(50) not null,
    primary key (hashtag_id)
);

DROP TABLE IF EXISTS hashtag_post;

create table hashtag_post (
    hashtag_post_id bigint not null AUTO_INCREMENT,
    post_id bigint not null,
    hashtag_id bigint not null,
    primary key (hashtag_post_id),
    FOREIGN key (post_id) REFERENCES post (post_id),
    FOREIGN key (hashtag_id) REFERENCES hashtag (hashtag_id)
);