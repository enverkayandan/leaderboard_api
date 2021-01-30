create table ranking_users
(
    id           uuid primary key,
    points       double precision not null,
    display_name text not null,
    country_iso  text not null,
    up_user      uuid
        constraint fk_ranking_user_up
            references ranking_users,
    down_user    uuid
        constraint fk_ranking_user_down
            references ranking_users
)