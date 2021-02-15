drop table if exists task_domain
drop table if exists list_domain
create table list_domain (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table task_domain (id bigint not null auto_increment, description varchar(255) not null, is_done bit not null, my_list_id bigint, primary key (id)) engine=InnoDB
alter table task_domain add constraint FKjy3rnh62nume9qkpjui57vben foreign key (my_list_id) references list_domain (id) on delete cascade