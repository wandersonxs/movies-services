# API Movies Services
This API project is a test for the role of Java Software Engineer at Texo IT.
It is an API RESTful, which sets up the environment at the bootstrap, reading a csv file and
populating a database with the data contained into the csv.
The main purpouse is to get a list of winners based in the following requirement:
- Get the producers with the major interval between two consecutive prizes.
- Get the producers with the shortest interval between two prizes.

## Architecture
It is RestFull microservices that follow the Richardson Maturity Model, level 2.
Followed the basics principles of SOLID and Clear Code.
Tried at most to use declarative code instead of imperative code.

### STACKS
- Java 17
    - Spring Boot 3.x
    - Spring Data JPA
    - Maven
    - H2 Database
- Integration Test 
  - JUnit 5
  - MockMvc
  - JsonPath
- OpenAPI 3.x Specification
- Swagger API Documentation

## Pre-requirements to run the project
    - Java 17
    - Maven
    - JAVA_HOME set correctly

## How to run the project 

1 - Clone the project.

git clone https://github.com/wandersonxs/movies-services

2 - Go to the project folder.

3 - Run command:

```
mvn spring-boot:run
```

This will run the api on port 8080.

5 - Access the swagger through http://localhost:8080/swagger-ui/index.html

6 - At swagger you have all the endpoints and payloads to test

## How to run the integration tests 

1 - Clone the project.

git clone https://github.com/wandersonxs/movies-services

2 - Go to the project folder.

3 - Testing commands:

3.1 - To test the BootstrapTest class, which is in charge of testing the bootstrap of project. Read csv and load its data to
database.

```
mvn test -Dtest="BootstrapTest"
```

3.2 - To test the ProducerResourceTest class, which has the producers CRUD endpoints and the main endpoint to get the 
producers winners.

```
mvn test -Dtest="ProducerResourceTest"
```

3.3 - To test the MovieResourcetest class, which has the movies CRUD endpoints.

```
mvn test -Dtest="MovieResourceTest"
```