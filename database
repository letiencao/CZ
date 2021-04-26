use czone;
create table account
(
	id bigint not null primary key auto_increment,
    deleted bool not null,
    createddate timestamp not null,
    createdby varchar(255) not null,
    modifieddate timestamp null,
    modifiedby varchar(255) null,
    name varchar(255) not null,
    password text not null,
    phonenumber varchar(255) not null,
    avatar longtext null,
    uuid varchar(255) not null
);
create table post
(
	id bigint not null primary key auto_increment,
    deleted bool not null,
    createddate timestamp not null,
    createdby varchar(255) not null,
    modifieddate timestamp null,
    modifiedby varchar(255) null,
    content text not null,
    accountid bigint not null
);
create table comment
(
	id bigint not null primary key auto_increment,
    deleted bool not null,
    createddate timestamp not null,
    createdby varchar(255) not null,
    modifieddate timestamp null,
    modifiedby varchar(255) null,
    content text not null,
    postid bigint not null,
    accountid bigint not null
);
create table chat
(
	id bigint not null primary key auto_increment,
    deleted bool not null,
    createddate timestamp not null,
    createdby varchar(255) not null,
    modifieddate timestamp null,
    modifiedby varchar(255) null,
    content text not null,
    idA bigint not null,
    idB bigint not null
);
create table blocks
(
	id bigint not null primary key auto_increment,
    deleted bool not null,
    createddate timestamp not null,
    createdby varchar(255) not null,
    modifieddate timestamp null,
    modifiedby varchar(255) null,
    idblocks bigint not null,
    idblocked bigint not null
); 
create table friend
(
	id bigint not null primary key auto_increment,
    deleted bool not null,
    createddate timestamp not null,
    createdby varchar(255) not null,
    modifieddate timestamp null,
    modifiedby varchar(255) null,
    idA bigint not null,
    idB bigint not null,
    is_friend bool not null
);
create table likes
(
	id bigint not null primary key auto_increment,
    deleted bool not null,
    createddate timestamp not null,
    createdby varchar(255) not null,
    modifieddate timestamp null,
    modifiedby varchar(255) null,
    postid bigint not null,
    accountid bigint not null
);
create table file
(
	id bigint not null primary key auto_increment,
    deleted bool not null,
    createddate timestamp not null,
    createdby varchar(255) not null,
    modifieddate timestamp null,
    modifiedby varchar(255) null,
    content longtext null,
    postid bigint not null
    
);

create table report
(
	id bigint not null primary key auto_increment,
    deleted bool not null,
    createddate timestamp not null,
    createdby varchar(255) not null,
    modifieddate timestamp null,
    modifiedby varchar(255) null,
    details varchar(255) not null,
    accountid bigint not null,
    postid bigint not null,
    typereportid bigint not null
);
create table typereport
(
	id bigint not null primary key auto_increment,
    deleted bool not null,
    name varchar(255) not null
);
alter table file add constraint fk_file_post foreign key (postid) references post(id);
alter table blocks add constraint fk_blocks_account_1 foreign key (idblocks) references account(id);
alter table blocks add constraint fk_blocks_account_2 foreign key (idblocked) references account(id);
alter table post add constraint fk_post_account foreign key (accountid) references account(id);
alter table comment add constraint fk_comment_post foreign key (postid) references post(id);
alter table likes add constraint fk_likes_account foreign key (accountid) references account(id);
alter table likes add constraint fk_likes_post foreign key (postid) references post(id);
alter table comment add constraint fk_comment_account foreign key (accountid) references account(id);

alter table friend add constraint fk_friend_account_1 foreign key (idA) references account(id);
alter table friend add constraint fk_friend_account_2 foreign key (idB) references account(id);

alter table chat add constraint fk_chat_account_1 foreign key (idA) references account(id);
alter table chat add constraint fk_chat_account_2 foreign key (idB) references account(id);

alter table report add constraint fk_report_account foreign key (accountid) references account(id);
alter table report add constraint fk_report_post foreign key (postid) references post(id);
alter table report add constraint fk_report_typereport foreign key (typereportid) references typereport(id);
INSERT INTO typereport(deleted,name) VALUES(false,"Nội dung nhạy cảm");
INSERT INTO typereport(deleted,name) VALUES(false,"Làm phiền");
INSERT INTO typereport(deleted,name) VALUES(false,"Lừa đảo");
INSERT INTO typereport(deleted,name) VALUES(false,"Lí do khác")
