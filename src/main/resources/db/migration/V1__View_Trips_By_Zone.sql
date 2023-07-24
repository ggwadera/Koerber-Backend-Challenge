create view trips_by_zone as
with pickups as (select pickup_location_id as l_id, count(pickup_location_id) as pu_total
                 from trip
                 group by pickup_location_id),
     dropoffs as (select dropoff_location_id as l_id, count(dropoff_location_id) do_total
                  from trip
                  group by dropoff_location_id),
     joined as (select pickups.l_id, pu_total, do_total
                from pickups
                         join dropoffs on pickups.l_id = dropoffs.l_id)
select l.zone as zone, joined.pu_total as pickup_total, joined.do_total as dropoff_total
from joined
         left join location l on l_id = l.id;