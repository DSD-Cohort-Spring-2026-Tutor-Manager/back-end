# Use OpenJDK 21 as the base image
<<<<<<< HEAD
<<<<<<< HEAD
FROM eclipse-temurin:21-jdk-jammy
=======
FROM openjdk:21-jdk-slim
>>>>>>> 8033020 (feat: #0 Dockerfile added (#13))
=======
FROM eclipse-temurin:21-jdk-jammy
>>>>>>> 7d83752 (feat: #0 Dockerfile  updated for executing (#14))

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
<<<<<<< HEAD
<<<<<<< HEAD
COPY target/demo*.jar app.jar
=======
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
>>>>>>> e23037b (chore: #2 Sync Integration Branch with Main (#18))
=======
COPY target/demo*.jar app.jar
>>>>>>> 21d1890 (Corrected package structue, imports and corrected test cases)

# Expose port 8080 to access the application
EXPOSE 8080

<<<<<<< HEAD
<<<<<<< HEAD
=======
# Run the Spring Boot application
>>>>>>> 8033020 (feat: #0 Dockerfile added (#13))
=======
>>>>>>> 7d83752 (feat: #0 Dockerfile  updated for executing (#14))
ENTRYPOINT ["java", "-jar", "app.jar"]