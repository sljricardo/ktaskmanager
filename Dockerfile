# Use the official Gradle image to build the project
FROM gradle:8.5-jdk21 AS build

WORKDIR /app

# Install SQLite in the build container
RUN apt-get update && apt-get install -y sqlite3

COPY --chown=gradle:gradle . .

# SQLite database
RUN mkdir -p /app && touch /app/mydb.db

# Copy the SQL migration file into the container
COPY ./src/main/resources/db/migration/V1__migrations.sql /app/V1__migrations.sql

# Run the V1__migrations inside the build container
RUN sqlite3 /app/mydb.db < /app/V1__migrations.sql

# Run jOOQ code generation
RUN gradle generateJooq

# Build the project (excluding tests)
RUN gradle bootJar -x test

# Use a lightweight JDK runtime for the final image
FROM openjdk:21-jdk-slim

WORKDIR /app

# Set environment variables
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Copy the built jar
COPY --from=build /app/build/libs/*.jar app.jar

# Copy the database to persist it in the final container
COPY --from=build /app/mydb.db /app/mydb.db

# Expose application port
EXPOSE 8000

# Run migrations before starting the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
