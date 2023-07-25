create materialized view trips_by_zone_and_date as
with pickups as (select pickup_location_id        as l_id,
                        date(pickup_datetime)     as pu_date,
                        count(pickup_location_id) as pu_total
                 from trip
                 group by pickup_location_id, pu_date),
     dropoffs as (select dropoff_location_id    as  l_id,
                         date(dropoff_datetime) as  do_date,
                         count(dropoff_location_id) do_total
                  from trip
                  group by dropoff_location_id, do_date),
     joined as (select pickups.l_id, pu_date, pu_total, do_total
                from pickups
                join dropoffs on pickups.l_id = dropoffs.l_id and pu_date = do_date)
select joined.l_id     as location_id,
       joined.pu_date  as date,
       joined.pu_total as pickup_total,
       joined.do_total as dropoff_total
from joined
left join location l on l_id = l.id;

create index idx_location_date on trips_by_zone_and_date(location_id, date);