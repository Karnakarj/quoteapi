# Use Java 17 base image (Spring Boot works well with it)
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy Maven wrapper and pom.xml first (for caching dependencies)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (this step is cached unless pom.xml changes)
RUN ./mvnw dependency:go-offline -B

# Copy the actual project source
COPY src src

# Build the JAR (skip tests to make it faster)
RUN ./mvnw clean package -DskipTests

# Run the JAR (adjust the jar name if needed)
CMD ["java", "-jar", "target/quoteapi-0.0.1-SNAPSHOT.jar"]
