FROM maven:3.8-openjdk-17-slim as builder

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and download the dependencies
COPY pom.xml .

RUN mvn dependency:go-offline

# Copy the source code to the container
COPY src/ ./src/

# Build the application
RUN mvn package -DskipTests

# Create a new Docker image with only the built artifact
FROM openjdk:17-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built artifact from the previous stage
COPY --from=builder /app/target/*.jar ./app.jar

# Expose the port your Spring Boot application listens on
EXPOSE 8080

# Set the entrypoint command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
