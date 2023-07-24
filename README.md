# **Getting Started**

### **Start-up**

Before running this project, make sure you have the dataset available on your local copy of the repo.
The files `zone.csv`, `green.csv` and `yellow.csv` can be downloaded from here https://easyupload.io/m/122a7h.

Put the CSV files inside the `db/data` folder.
The initialization scripts will read from these files to seed the database.

Run the following commands in the command line:

```shell
mvn clean install
docker build -t challenge-1.0.0.jar .
docker-compose up -d
```
