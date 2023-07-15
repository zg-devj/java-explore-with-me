-- для тестирования
drop table if exists compilation_events;
drop table if exists compilations;
drop table if exists requests;
drop table if exists events;
drop table if exists categories;
drop table if exists users;

-- пользователи
create table if not exists users
(
    id    bigint generated always as identity,
    name  varchar(250) not null,
    email varchar(254) not null,
    constraint pk_users primary key (id),
    constraint uq_users_email unique (email)
);

-- категории
create table if not exists categories
(
    id   bigint generated always as identity,
    name varchar(50) not null,
    constraint pk_categories primary key (id),
    constraint uq_categories_name unique (name)
);

-- события
create table if not exists events
(
    id                 bigint generated always as identity,
    title              varchar(120)                not null,
    user_id            bigint                      not null,
    category_id        bigint                      not null,
    location_lat       real                        not null,
    location_lon       real                        not null,
    annotation         varchar(2000)               not null,
    description        varchar(7000)               not null,
    created_on         timestamp without time zone not null default now(),
    event_date         timestamp without time zone,
    published_on       timestamp without time zone          default null,
    paid               boolean                     not null default false,
    confirmed_requests int                         not null default 0,
    participant_limit  int                         not null default 0,
    request_moderation boolean                     not null default true,
    state              varchar(9)                  not null default 'PENDING',
    constraint pk_events primary key (id),
    constraint fk_events_users foreign key (user_id) references users (id),
    constraint fk_events_categories foreign key (category_id) references categories (id) on delete restrict
);

-- запросы на участие
create table if not exists requests
(
    id           bigint generated always as identity,
    created      timestamp without time zone not null default now(),
    event_id     bigint                      not null,
    requester_id bigint                      not null,
    status       varchar(9)                  not null default 'PENDING',
    constraint pk_requests primary key (id),
    constraint fk_requests_events foreign key (event_id) references events (id),
    constraint fk_requests_users foreign key (requester_id) references users (id),
    constraint uq_requests unique (event_id, requester_id)
);

-- подборки
create table if not exists compilations
(
    id     bigint generated always as identity,
    title  varchar(50) not null,
    pinned boolean     not null default false,
    constraint pk_compilations primary key (id),
    constraint uq_compilations_title unique (title)
);

-- отношение подборок и событий
create table if not exists compilation_events
(
    compilation_id bigint not null
        constraint fk_compilation_events_compilations references compilations,
    event_id       bigint not null
        constraint fk_compilation_events_events references events,
    constraint pk_compilation_events primary key (compilation_id, event_id)
);

-- CREATE OR REPLACE FUNCTION distance(lat1 float, lon1 float, lat2 float, lon2 float)
--     RETURNS float
-- AS
-- '
--     declare
--         dist float = 0;
--         rad_lat1 float;
--         rad_lat2 float;
--         theta float;
--         rad_theta float;
--     BEGIN
--         IF lat1 = lat2 AND lon1 = lon2
--         THEN
--             RETURN dist;
--         ELSE
--             -- переводим градусы широты в радианы
--             rad_lat1 = pi() * lat1 / 180;
--             -- переводим градусы долготы в радианы
--             rad_lat2 = pi() * lat2 / 180;
--             -- находим разность долгот
--             theta = lon1 - lon2;
--             -- переводим градусы в радианы
--             rad_theta = pi() * theta / 180;
--             -- находим длину ортодромии
--             dist = sin(rad_lat1) * sin(rad_lat2) + cos(rad_lat1) * cos(rad_lat2) * cos(rad_theta);
--
--             IF dist > 1
--             THEN dist = 1;
--             END IF;
--
--             dist = acos(dist);
--             -- переводим радианы в градусы
--             dist = dist * 180 / pi();
--             -- переводим градусы в километры
--             dist = dist * 60 * 1.8524;
--
--             RETURN dist;
--         END IF;
--     END;
-- '
--     LANGUAGE PLPGSQL;