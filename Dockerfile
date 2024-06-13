FROM eclipse-temurin:17.0.8.1_1-jdk-nanoserver-1809
WORKDIR /app
COPY target/ebank-0.0.1-SNAPSHOT.jar ebank.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "imc.jar"]