# Use a minimal OpenJDK image
FROM openjdk:17-jdk-slim

# Add the JAR file (use wildcard to handle SNAPSHOT version)
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Expose the port Spring Boot runs on
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
