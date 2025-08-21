# Use OpenJDK 17 as base image
FROM eclipse-temurin:17-jre

# Set working directory
WORKDIR /app

# Copy the built jar file (update the jar name as needed)
COPY target/scholarship-0.0.1-SNAPSHOT.jar app.jar

# Expose port (update if your app uses a different port)
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]