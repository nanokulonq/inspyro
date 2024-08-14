create schema if not exists ideas;

create table ideas.t_idea(
    id serial primary key,
    c_title varchar(50) not null check (length(trim(c_title)) >= 3),
    c_description varchar(500) not null check (length(trim(c_description)) >= 3)
);