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