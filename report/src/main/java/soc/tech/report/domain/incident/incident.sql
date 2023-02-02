create table incidents
(
    id         int not null primary key,
    name       text,
    status     text,
    priority   text,
    executor   text,
    start_date text,
    due_date   text
);