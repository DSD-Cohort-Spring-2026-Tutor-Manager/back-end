# Stage 1: Build
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
COPY pom.xml .
COPY src src

RUN mvn clean package -DskipTests

# Stage 2: Create the final Docker image using eclipse-temurin
FROM eclipse-temurin:21-jdk-jammy
VOLUME /tmp

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080