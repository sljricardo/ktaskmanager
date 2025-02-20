## Ktaskmanager

### Description
Basic CRUD API to manage tasks

### Requirements
 - sqlite

### Development
1. create the tables from 'src/main/resources/db/migration' 
2. run jooq generator ./gradlew generateJooq 
3. run the app

### Jacoco
Project leverage jacoco for test coverage to run it you can execute the following command
> ./gradlew test jacocoTestReport


### Requests
you have an [Insomnia](https://insomnia.rest/) [file](Insomnia_v1.json) with all http request, that you can use for local development