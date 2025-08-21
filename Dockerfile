# Stage 1: Build the application with Maven and JDK 17
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the project's source code and Maven pom.xml
COPY pom.xml .
COPY src ./src

# Build the application, skipping tests to speed up the process
RUN mvn clean package -DskipTests

# Stage 2: Create the final, lightweight image
FROM eclipse-temurin:17-jre

# Set the working directory
WORKDIR /app

# Copy the jar from the build stage to the final image
# Using a wildcard (*) is more flexible than a hardcoded version
COPY --from=build /app/target/*.jar app.jar

# Expose the application's port
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]