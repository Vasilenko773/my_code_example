create table time_entries
(
    id          int not null primary key,
    system_id   int,
    project_id  int references projects(id),
    issues_id   int references issues(id),
    user_id     int references users(id),
    activity_id int references activity(id),
    hours       double precision,
    comments    text,
    spent_on    timestamp,
    created     text,
    updated     text
);