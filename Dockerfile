# Use an official OpenJDK runtime as a parent image
FROM openjdk:22-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the compiled JAR file from the target directory to the working directory
COPY target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
