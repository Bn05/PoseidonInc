# PoseidonInc

PoseidonApp is an application of PoseidonInc. Using for generate transactions.

## About The Project

Web application prototype for Openclassrooms, project number 7.
This is a Spring Boot application.

## Getting Started

### Prerequisites

Check that you have :

* Apache Maven 3.8.6 installed
* Java 17 installed
* mySQL 8.0.31 installed

### Installation

1. Connect to your mysql Datasources
2. Run data.sql

3. Set environment variables "spring.datasource.username" -> your mySQL username (username in comment in application.properties)
4. Set environment variables "spring.datasource.password" -> your mySql password (password in comment in application.properties)

5. Select the PoseidonInc directory
6. To run test and package
7. ```sh
    mvn clean verify site
   ```
8. Execute
   ```sh
    java -jar target/PoseidonInc-0.0.1-SNAPSHOT.jar
   ```
9. To access the WebApp, open your browser, go to [http://localhost:8080](http://localhost:8080)
10. To access the API, call [http://localhost:8080](http://localhost:8080). You can find the documentation [here](https://documenter.getpostman.com/view/24252349/2s8ZDcyfDv)(In Progress).
11. You can use my PostMan : [here](https://www.postman.com/bertrandnoel/workspace/poseidonincapi/collection/24252349-cb1121c5-a8e3-4b0d-9e2e-a30d8d79290e?action=share&creator=24252349) (Work only with desktop version)
12. Admin user is created : userName = admin, password = Password1234!.