# Build stage
FROM openjdk:17-alpine AS build

WORKDIR /workspace

# Copy Gradle Wrapper, config files and source code
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

# Give execute permission to gradlew
RUN chmod +x gradlew

# Perform build
RUN ./gradlew bootJar -x test

# Runtime stage
FROM openjdk:17-alpine

# Copy JAR file generated from build stage
COPY --from=build /workspace/build/libs/*.jar app.jar

# Run application
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=server", "/app.jar"]