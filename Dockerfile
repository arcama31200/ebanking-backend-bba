# Utilisation de l'image officielle Maven avec OpenJDK 17
FROM maven:3.8.5-openjdk-17-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier les fichiers Maven Wrapper
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Télécharger les dépendances Maven
RUN ./mvnw dependency:go-offline

# Copier les sources de l'application
COPY src ./src

# Exposer le port de l'application
EXPOSE 8080

# Commande à exécuter pour démarrer l'application Spring Boot
CMD ["./mvnw", "spring-boot:run"]
