create table chats_employees_tg
(
    chat_id bigint references chats_tg (id),
    employee_id int references employee_tg (id)
);