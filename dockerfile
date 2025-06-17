# ----------- Stage 1: Build the app -----------
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy everything into the container
COPY . .

# Build the Spring Boot project
RUN mvn clean package -DskipTests


# ----------- Stage 2: Run the app -----------
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy only the final JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the app port
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
