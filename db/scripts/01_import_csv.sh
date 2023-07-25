#!/bin/bash
set -e

CSV_PATH=/opt/data

# Import data from the CSV files
# From the green and yellow trips CSV, the files are pre-processed to import only the required columns
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
	COPY location FROM '$CSV_PATH/zone.csv' WITH (FORMAT CSV, DELIMITER ',', HEADER);

	COPY trip(pickup_datetime, dropoff_datetime, pickup_location_id, dropoff_location_id)
	FROM PROGRAM 'cut --delimiter "," --fields 3,4,7,8 $CSV_PATH/green.csv' WITH (FORMAT CSV, DELIMITER ',', HEADER);

	COPY trip(pickup_datetime, dropoff_datetime, pickup_location_id, dropoff_location_id)
	FROM PROGRAM 'cut --delimiter "," --fields 3,4,9,10 $CSV_PATH/yellow.csv' WITH (FORMAT CSV, DELIMITER ',', HEADER);
EOSQL