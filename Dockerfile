# Use a base image with Java
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR file (adjust the path and name)
COPY build/libs/wiz.jar wiz.jar

# Set command to run the JAR
ENTRYPOINT ["java", "-jar", "wiz.jar"]
