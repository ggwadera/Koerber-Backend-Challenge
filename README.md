# Getting Started

# Start-up

Before running this project, make sure you have the dataset available on your local copy of the repo.
The files `zone.csv`, `green.csv` and `yellow.csv` can be downloaded from here https://easyupload.io/m/122a7h.

Put the CSV files inside the `db/data` folder.
The initialization scripts will read from these files to seed the database.

Run the following commands in the command line:

```shell
./mvnw clean package
docker build -t challenge-1.0.0.jar .
docker-compose up -d
```

*Remark*: the database initialization scripts are executed when the container starts,
and it may take some time until the database is available as there are around 3 million lines to copy.
On my machine it takes around 30 seconds to initialize the database on the first start.

The OpenAPI documentation for the server will be available at http://localhost:8080/challenge/swagger-ui.html.

## Endpoints

Below are listed some examples for curl commands for the challenge APIs:

### Top zones

Returns a list of the first 5 zones order by number of total pickups or the number of total drop-offs.
Accepts 1 parameter:

- *sort*: either `pickups` or `dropoffs`, defines the resulting list sorting order.

```shell
curl -X 'GET' \
  'http://localhost:8080/challenge/top-zones?sort=pickups' \
  -H 'accept: application/json'
```

### Zone trips

Returns the sum of the pickups and drop-offs in just one zone and one date.
Accepts 2 parameters:

- *zone*: the zone id of any of the available zones
- *date*: the date to search

```shell
curl -X 'GET' \
  'http://localhost:8080/challenge/zone-trips?zone=135&date=2023-01-01' \
  -H 'accept: application/json'
```

### List yellow

Returns data from the trips files with pagination + filtering and multiple sort.
This endpoint accepts several parameters:

- *page*: page number (starts at 0)
- *size*: page size (default = 20)
- *sort*: sorting criteria, in the format: property,(asc|desc) (example: `pickupDateTime,asc`)
- *pickupLocationId*: filter by pickup location ID
- *dropoffLocationId*: filter by drop-off location ID
- *pickupDateTime*: filter by pickup date
- *pickupDateTimeStart*: filter by pickup date greater than this
- *pickupDateTimeEnd*: filter by pickup date less than this
- *dropoffDateTime*: filter by drop-off date
- *dropoffDateTimeStart*: filter by drop-off date greater than this
- *dropoffDateTimeEnd*: filter by drop-off date less than this

```shell
curl -X 'GET' \
  'http://localhost:8080/challenge/list-yellow?page=0&size=20&sort=pickupLocation.id,asc&sort=pickupDateTime,desc&pickupDateTimeStart=2023-01-20' \
  -H 'accept: application/json'
```

## Implementation details

### Data loading

To load the data into the database I used the PostgreSQL container initialization scripts folder.
The CSV files and scripts are mounted into the container volume and are executed on the first start.
This way it's possible to load the data using the tools provided natively by Postgres such as `COPY FROM ...`.
To load only the required columns from the `green.csv` and `yellow.csv` files, the input is pre-processed with `cut`
to extract only the columns for pickup and drop-off timestamps and zone ids before inserting into the table.

In a production setting there's many ways to accomplish this automated data loading with periodic updates.
A cron job could be setup to load the data into the database with scripts such as the one I made.
This could also be done on the application side with some libraries like Spring Batch as well.

### Top zones

To get the top zones by pickups and drop-offs totals I used a view with a few CTEs to make the query easier on the back-end.

```sql
create view trips_by_zone as
with pickups as (select pickup_location_id as l_id, count(pickup_location_id) as pu_total
                 from trip group by pickup_location_id),
     dropoffs as (select dropoff_location_id as l_id, count(dropoff_location_id) do_total
                  from trip group by dropoff_location_id),
     joined as (select pickups.l_id, pu_total, do_total
                from pickups join dropoffs on pickups.l_id = dropoffs.l_id)
select l.zone as zone, joined.pu_total as pickup_total, joined.do_total as dropoff_total
from joined left join location l on l_id = l.id;
```

The CTEs first creates a table with the total pickups per zone, then the total drop-offs per zone, and finally joins
them using the zone ID to get the total pickups and drop-offs for every zone.
This is then joined with the locations table to get the zone name from the IDs.

On the back-end side, this view in then used along with `order by` and `limit 5` to return the top 5 zones sorted by
pickups or drop-offs.
From my testing this view and the query performs very well, returning the zones and totals under a few milliseconds,
which I found to be pretty good considering that there's over 3 million row on the table. 
If performance becomes a problem, this could be turned into a materialized view so the totals are all pre-calculated.

### Zone trips

Again, as the query is very similar to the last one, I used a view to simplify the query on the server.

```sql
create materialized view trips_by_zone_and_date as
with pickups as (select pickup_location_id        as l_id,
                        date(pickup_datetime)     as pu_date,
                        count(pickup_location_id) as pu_total
                 from trip group by pickup_location_id, pu_date),
     dropoffs as (select dropoff_location_id    as  l_id,
                         date(dropoff_datetime) as  do_date,
                         count(dropoff_location_id) do_total
                  from trip group by dropoff_location_id, do_date),
     joined as (select pickups.l_id, pu_date, pu_total, do_total
                from pickups join dropoffs on pickups.l_id = dropoffs.l_id and pu_date = do_date)
select joined.l_id     as location_id,
       joined.pu_date  as date,
       joined.pu_total as pickup_total,
       joined.do_total as dropoff_total
from joined left join location l on l_id = l.id;

create index idx_location_date on trips_by_zone_and_date(location_id, date);
```

The difference now is that the totals are calculated per zone and per day. 
As this would result in many more rows I chose to use a materialized view and an index on the zone ID and date,
so the search is optimized if this table becomes very large.
And as this is a materialized view, after every data loading this table would need to be refreshed to include the new rows.

And again, on the back-end side this view is used to make the query simpler, only adding the `where` clauses for the zone ID and date.

### List yellow

As this is a simple search and ordering with pagination, this can be done directly on the application side.
To implement filtering I used JPA Criteria API along with Spring Data Specifications, to build a dynamic query
to filter based on zone IDs and pickup and/or drop-off dates.
The Pageable interface from Spring also handles the pagination and ordering.

### Tests

For the tests I decided to develop integration tests with Testcontainers, as there's little business logic on the
application side, and much more SQL details to be verified.
So all the queries are tested using a Postgres container to have the same runtime behavior as it would on a production environment,
but using a much smaller slice of the data, so it can be verified to be correct with manual observations.

For the endpoints tests I used `@WebMvcTest` from Spring, to check the response status and bodies, mocking the data that would be returned from the database.