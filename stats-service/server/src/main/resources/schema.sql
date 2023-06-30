drop table if exists statistics;

create table if not exists statistics
(
    id      bigint generated always as identity,
    app     varchar(20)  not null,
    uri     varchar(255) not null,
    ip      varchar(32)  not null,
    timestamp timestamp without time zone,
    constraint pk_stats primary key (id)
);