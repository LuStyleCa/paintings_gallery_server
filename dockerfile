# ----------- Stage 1: Build the app -----------
FROM maven:3.8.6-eclipse-temurin-17 AS build

# Set working directory in container
WORKDIR /app

# Copy everything into the container
COPY . .

# Build the project (skip tests if you want faster builds)
RUN mvn clean package -DskipTests


# ----------- Stage 2: Run the app -----------
FROM eclipse-temurin:23-jdk-slim

WORKDIR /app

# Copy only the JAR from the first stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
