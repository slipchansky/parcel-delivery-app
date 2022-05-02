# Parcel Delivery Service

## Task Description
Tasks Description.pdf
[Task Description]

### Disclaimer
The proposed solution in this form is not a commercial product. This example is rather just a POC that should be elaborated in details. What is done and what is not is described here: [What is Done and What is Not]

## Get the actual copy of project

```bash
https://github.com/slipchansky/parcel-delivery-app.git
```


## Starting From DockerHub

On Windows

```bash
cd docker-compose
mkdir local_postgres_data
up-docker-io.cmd
```

On Linux

```bash
cd docker-compose
mkdir local_postgres_data
chmod 777 up-docker-io.cmd
./up-docker-io.cmd

```

This composition runs all the service with single shared db server and single database (for saving the resources of host computer)

You also can find there 

```bash
up-db-per-service-compose-docker-io.cmd
```

That runs all the services with separate db server.


## Starting From Scratch


Build and perform unit tests

```bash
cd parcel-delivery-app
mvn package
```

### Run locally

You should have started instance of RabbitMQ that should be accessible from the host computer and listen to port 5672. 

Also the ports of 8000 - 80003, 3333 and 8080 should be free for binding the applications from this bundle.

#### On Windows

```bash
run-all-locally.cmd
```

#### On Linux

```bash
java -Dspring.profiles.active=dev -jar .\parceldelivery-eureka\target\parceldelivery-eureka-0.0.1-SNAPSHOT.jar
java -Dspring.profiles.active=dev -jar .\parcel-delivery-publicapi\target\parcel-delivery-publicapi-0.0.1-SNAPSHOT.jar
java -Dspring.profiles.active=dev -jar .\parceldelivery-admin-service\target\parceldelivery-admin-service-0.0.1-SNAPSHOT.jar
java -Dspring.profiles.active=dev -jar .\parceldelivery-client-service\target\parceldelivery-client-service-0.0.1-SNAPSHOT.jar
java -Dspring.profiles.active=dev -jar .\parceldelivery-courier-service\target\parceldelivery-courier-service-0.0.1-SNAPSHOT.jar
java -Dspring.profiles.active=dev -jar .\parceldelivery-user-service\target\parceldelivery-user-service-0.0.1-SNAPSHOT.jar
```



### Run in docker-compose
#### 1. Put everything into docker images

1.1. On Windows

```bash
docker-build-all.cmd
```

1.2. On linux

```bash
cd .\parceldelivery-user-service\
chmod 777 docker-build.cmd
./docker-build.cmd
cd ..
cd .\parceldelivery-client-service
chmod 777 docker-build.cmd
./docker-build.cmd
cd ..
cd .\parceldelivery-courier-service
chmod 777 docker-build.cmd
./docker-build.cmd
cd ..
cd .\parceldelivery-admin-service
chmod 777 docker-build.cmd
./docker-build.cmd
cd ..
cd .\parcel-delivery-publicapi
chmod 777 docker-build.cmd
./docker-build.cmd
cd ..
cd .\parceldelivery-eureka
chmod 777 docker-build.cmd
./docker-build.cmd
cd ..

```
#### 2. Run composition

```bash
cd docker-compose
```

2.1. Prepare local directory for containerized Postgre

```
mkdir local_postgres_data

```

2.2. Start composition

```
docker-compose -f compose.yml up
```

This composition runs all the service with single shared db server and single database (for saving the resources of host computer)

You also can find there 

```bash
up-db-per-service-compose.cmd
```

That runs all the services with separate db server.

### Swagger API documentation

Located at

```
http://localhost:8080/swagger-ui/
```

### Perform acceptance tests

For doing this part you should have installed python with modules of requests and pytest.

```bash
cd acceptance-tests
pytest
```


[//]: # (These are reference links used in the body of this note

[What is Done and What is Not]: <https://github.com/slipchansky/parcel-delivery-app/blob/main/doc/whatisdoneadwhatisnot.md>
[Task Description]: <https://github.com/slipchansky/parcel-delivery-app/blob/main/doc/Tasks Description.pdf>