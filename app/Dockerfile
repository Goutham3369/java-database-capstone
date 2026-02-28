# Stage 1: Build the application using Maven and JDK 17
# This stage compiles the source code and packages it into a JAR file.
FROM maven:3.9.9-eclipse-temurin-17 AS builder

# Set the working directory inside the builder container
WORKDIR /app

# Copy the pom.xml and source code to the container
COPY pom.xml .
COPY src ./src

# Build the application, skipping tests to speed up the process
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight runtime image using JRE 17
# This stage only contains the compiled application and the Java runtime.
FROM eclipse-temurin:17.0.15_6-jre

# Set the working directory for the runtime environment
WORKDIR /app

# Copy the built JAR file from the builder stage and rename it to app.jar
COPY --from=builder /app/target/back-end-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 to allow external access to the Spring Boot application
EXPOSE 8080

# Define the command to execute the application
ENTRYPOINT ["java", "-jar", "app.jar"]
