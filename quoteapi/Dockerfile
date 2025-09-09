# Use Java 17 base image
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the jar from target folder
COPY target/quoteapi-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
