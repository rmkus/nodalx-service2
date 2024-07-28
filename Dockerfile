# Use an official Java runtime as a parent image
FROM openjdk:8-jdk-alpine

# Set the working directory
WORKDIR /app

# Add the project JAR file to the container
COPY build/libs/nodalx-service2-1.0.5.jar app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
