create table custom_fields
(
    system_id int,
    name      text,
    value     text,
    issues_id int references issues (id)
);