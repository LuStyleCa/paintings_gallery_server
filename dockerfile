# Use a base image with Java
FROM openjdk:23-slim

# Set the working directory inside the container
WORKDIR /app

# Add the fat JAR built by Maven or Gradle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080
    
# Run the JAR
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
