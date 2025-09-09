# Use Java 17 base image
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the jar from your repo
COPY target/quoteapi-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Start the app
ENTRYPOINT ["java","-jar","/app/app.jar"]
