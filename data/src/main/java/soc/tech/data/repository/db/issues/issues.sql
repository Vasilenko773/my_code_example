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

