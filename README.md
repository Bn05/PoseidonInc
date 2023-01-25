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
3. Set environment variables "spring.datasource.url" -> jdbc:mysql://[YOURLMysql]/poseidondb?serverTimezone=UTC
4. Set environment variables "spring.datasource.username" -> your mySQL username
5. Set environment variables "spring.datasource.password" -> your mySql password

6. Select the PoseidonInc directory
7. To run test and package
8. ```sh
    mvn clean verify site
   ```
9. Execute
   ```sh
    java -jar target/PoseidonInc-0.0.1-SNAPSHOT.jar
   ```
10. To access the WebApp, open your browser, go to [http://localhost:8080](http://localhost:8080)