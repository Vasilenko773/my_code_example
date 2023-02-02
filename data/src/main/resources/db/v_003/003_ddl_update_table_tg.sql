drop table chats_employees_tg;
drop table employee_tg;
drop table chats_tg;

create table chats_tg
(
    id bigint not null primary key
);

create table employee_tg
(
    id   int not null primary key,
    system_id int,
    name text
);

create table chats_employees_tg
(
    chat_id bigint references chats_tg (id),
    employee_id int references employee_tg (id)
);
