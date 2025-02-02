# Use the official Gradle image to build the project
FROM gradle:8.5-jdk21 AS build

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper and project files
COPY --chown=gradle:gradle . .

# Build the project with the Kotlin Spring Boot plugin
RUN gradle bootJar -x test

# Use a lightweight JDK runtime for the final image
FROM openjdk:21-jdk-slim

# Set environment variables
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Set the working directory
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application port (adjust if needed)
EXPOSE 8000

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
