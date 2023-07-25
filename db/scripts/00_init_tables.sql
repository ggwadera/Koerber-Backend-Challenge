create table location
(
    id           bigint primary key,
    borough      varchar not null,
    zone         varchar not null,
    service_zone varchar not null
);

create table trip
(
    id                  bigserial primary key,
    pickup_datetime     timestamp not null,
    dropoff_datetime    timestamp not null,
    pickup_location_id  bigint    not null references location (id),
    dropoff_location_id bigint    not null references location (id)
);
