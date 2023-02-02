create table change_tasks
(
    id         int not null primary key,
    name       text,
    status     text,
    executor   text,
    priority   text,
    start_date text,
    deadline   text
);

create table dev_tasks
(
    id         int not null primary key,
    name       text,
    status     text,
    executor   text,
    priority   text,
    start_date text,
    deadline   text
);

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

create table time_worked
(
    date text,
    executor text,
    hours text
);