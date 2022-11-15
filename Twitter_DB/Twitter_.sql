DROP DATABASE twitter_db;
CREATE DATABASE twitter_db;

use twitter_db;

CREATE TABLE user (
user_id varchar(10) primary key,
password varchar(50) not null,
name varchar(50) not null,
phone varchar(13) not null,
email varchar(50) not null ,
description varchar(100),
create_at Timestamp NOT null DEFAULT CURRENT_TIMESTAMP
);


create table posts(
post_id bigint auto_increment primary key,
content text not null,
writer_id varchar(10) not null ,
date TIMESTAMP NOT NULL	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
FOREIGN KEY (writer_id) references user(user_id) ON UPDATE CASCADE
);

create table comment(
comment_id bigint auto_increment primary key,
writer_id varchar(10) not null,
post_id bigint not null,
content text not null,
child_comment_id bigint references comment(comment_id),
date TIMESTAMP NOT NULL	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
FOREIGN KEY (writer_id) references user(user_id) ON UPDATE CASCADE,
FOREIGN KEY (post_id) references posts(post_id) ON UPDATE CASCADE,
FOREIGN KEY (child_comment_id) references comment(comment_id) ON UPDATE CASCADE
);

create table post_mention(
m_p_id bigint auto_increment primary key,
post_id bigint not null,
mentioner_id varchar(10)  not null,
FOREIGN KEY (post_id) references posts(post_id) ON UPDATE CASCADE,
FOREIGN KEY (mentioner_id) references user(user_id) ON UPDATE CASCADE
);

create table comment_mention(
m_c_id bigint auto_increment primary key,
comment_id bigint not null,
mentioner_id varchar(10)  not null,
FOREIGN KEY (comment_id) references comment(comment_id) ON UPDATE CASCADE,
FOREIGN KEY (mentioner_id) references user(user_id) ON UPDATE CASCADE
);

create table post_hash(
p_h_id bigint auto_increment primary key,
post_id bigint not null ,
p_content varchar(20) not null,
FOREIGN KEY (post_id) references posts(post_id) ON UPDATE CASCADE
);
#user id 칼럼값 삭제

create table comment_hash(
c_h_id bigint  auto_increment primary key,
comment_id bigint not null,
h_content varchar(20) not null,
FOREIGN KEY (comment_id) references comment(comment_id) ON UPDATE CASCADE
);

create table re_tweet_post (
r_p_id bigint auto_increment primary key,
post_id bigint not null,
user_id varchar(10) not null ,
create_at timestamp not null default current_timestamp,
FOREIGN KEY (post_id) references posts(post_id) ON UPDATE CASCADE,
FOREIGN KEY (user_id) references user(user_id) ON UPDATE CASCADE
);

select * from following;
create table following(
follower_id varchar(10) not null,
followed_id varchar(10) not null,
create_at Timestamp NOT null DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY(follower_id, followed_id),
FOREIGN KEY (follower_id) references user(user_id) ON UPDATE CASCADE,
FOREIGN KEY (followed_id) references user(user_id) ON UPDATE CASCADE
);

select * from block;
create table block(
user_id varchar(10) not null,
block_id varchar(10) not null,
create_at Timestamp NOT null DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY(user_id, block_id ),
FOREIGN KEY (user_id) references user(user_id) ON UPDATE CASCADE,
FOREIGN KEY (block_id) references user(user_id) ON UPDATE CASCADE
);

select * from quote_post;
create table quote_post(
post_id bigint not null,
q_post_id bigint not null,
user_id varchar(10) not null ,
create_at timestamp not null default current_timestamp,
PRIMARY KEY(post_id, q_post_id),
FOREIGN KEY (post_id) references posts(post_id) ON UPDATE CASCADE,
FOREIGN KEY (user_id) references user(user_id) ON UPDATE CASCADE
);

create table post_like(
l_p_id bigint auto_increment primary key,
post_id bigint not null,
liker_id varchar(10) not null,
create_at Timestamp NOT null DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (post_id) references posts(post_id) ON UPDATE CASCADE,
FOREIGN KEY (liker_id) references user(user_id) ON UPDATE CASCADE
);

create table comment_like(
l_c_id bigint auto_increment primary key,
comment_id bigint not null,
liker_id varchar(10) not null,
create_at Timestamp NOT null DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (comment_id) references comment(comment_id) ON UPDATE CASCADE,
FOREIGN KEY (liker_id) references user(user_id) ON UPDATE CASCADE
);