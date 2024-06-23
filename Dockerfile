# backend/Dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . /app

# Compile et exécute l'application
CMD ["./mvnw", "spring-boot:run"]
