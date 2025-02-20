## Ktaskmanager

### Description
Basic CRUD API to manage tasks

### Requirements
 - sqlite

### Development
1. create the tables from 'src/main/resources/db/migration' 
2. run jooq generator ./gradlew generateJooq 
3. run the app

### Build
> ./gradlew build

> java -jar build/libs/tkmanager-0.0.1-SNAPSHOT.jar

### Jacoco
Project leverage jacoco for test coverage to run it you can execute the following command
> ./gradlew test jacocoTestReport

### Swagger
OpenAPI documentation is provided through
> ./gradlew bootRun

then access to 
> http://localhost:8000/swagger-ui.html

### Requests
you have an [Insomnia](https://insomnia.rest/) [file](Insomnia_v1.json) with all http request, that you can use for local development