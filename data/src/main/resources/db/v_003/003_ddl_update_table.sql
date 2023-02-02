drop table time_entries;
drop table custom_fields;
drop table issues;
drop table users;
drop table projects;
drop table activity;


create table projects
(
    id        serial primary key,
    projectId int,
    system_id int,
    name      text
);

create table users
(
    id        serial primary key,
    userId    int,
    system_id int,
    name      text
);

create table activity
(
    id         serial primary key,
    activityId int,
    system_id  int,
    name       text
);


create table issues
(
    id              serial primary key,
    issueId         int,
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
    id             serial primary key,
    time_entriesId int,
    system_id      int,
    project_id     int references projects (id),
    issues_id      int references issues (id),
    user_id        int references users (id),
    activity_id    int ,
    hours          double precision,
    comments       text,
    spent_on       timestamp,
    created        text,
    updated        text
);

create table custom_fields
(
    id serial primary key,
    system_id int,
    name      text,
    value     text,
    issues_id int references issues (id)
);