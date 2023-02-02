create table projects
(
    id        int not null primary key,
    system_id int,
    name      text
);

create table users
(
    id        int not null primary key,
    system_id int,
    name      text
);

create table activity
(
    id        int not null primary key,
    system_id int,
    name      text
);


create table issues
(
    id              int not null primary key,
    system_id       int,
    project_id      int references projects (id),
    type            text,
    status          text,
    priority        text,
    user_id         int references users (id),
    name            text,
    description     text,
    start_date      timestamp,
    due_date        timestamp,
    done_ratio      int,
    estimated_hours double precision,
    created         text,
    updated         text,
    closed          text
);

create table time_entries
(
    id          int not null primary key,
    system_id   int,
    project_id  int references projects (id),
    issues_id   int references issues (id),
    user_id     int references users (id),
    activity_id int references activity (id),
    hours       double precision,
    comments    text,
    spent_on    timestamp,
    created     text,
    updated     text
);

create table custom_fields
(
    system_id int,
    name      text,
    value     text,
    issues_id int references issues (id)
);