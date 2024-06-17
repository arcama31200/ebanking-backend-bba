# Utilisation de l'image de base OpenJDK
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier uniquement le fichier pom.xml et télécharger les dépendances
COPY pom.xml .
RUN ./mvnw dependency:go-offline

# Préparer l'environnement Maven Wrapper
COPY .mvn .mvn
COPY mvnw .

# Exposer le port 8080
EXPOSE 8080

# Commande par défaut pour démarrer l'application avec des modifications à chaud
CMD ["./mvnw", "spring-boot:run"]
