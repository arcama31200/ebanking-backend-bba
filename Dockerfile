# Utiliser une image de base pour OpenJDK 17
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier les fichiers du projet dans le répertoire de travail
COPY . .

# Pré-compiler le projet pour permettre les modifications à chaud
RUN ./mvnw compile

# Exposer le port 8080 pour l'application Java
EXPOSE 8080

# Commande pour démarrer l'application en mode développement
CMD ["./mvnw", "spring-boot:run"]
