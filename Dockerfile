# Stage 1: Build the application
FROM gradle:7.5.1-jdk17 AS build
WORKDIR /app

# Disable the Gradle daemon for faster builds in Docker
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false"

# Copy Gradle files and the project source code
COPY build.gradle settings.gradle /app/
COPY src /app/src

# Build the application using Gradle
RUN gradle clean build -x test

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application's port (default for Spring Boot is 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
