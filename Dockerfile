# Use OpenJDK 21 as the base image
<<<<<<< HEAD
FROM eclipse-temurin:21-jdk-jammy
=======
FROM openjdk:21-jdk-slim
>>>>>>> 8033020 (feat: #0 Dockerfile added (#13))

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
<<<<<<< HEAD
COPY target/demo*.jar app.jar
=======
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
>>>>>>> e23037b (chore: #2 Sync Integration Branch with Main (#18))

# Expose port 8080 to access the application
EXPOSE 8080

<<<<<<< HEAD
=======
# Run the Spring Boot application
>>>>>>> 8033020 (feat: #0 Dockerfile added (#13))
ENTRYPOINT ["java", "-jar", "app.jar"]