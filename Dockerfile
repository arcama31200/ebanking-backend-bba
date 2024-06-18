# Utilisation de l'image de base OpenJDK 17 Slim
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier les fichiers de configuration Maven et les scripts Maven Wrapper
COPY .mvn/ .mvn/
COPY mvnw .
COPY pom.xml .

# Supprimer les caractères de retour (\r) du fichier mvnw
RUN sed -i 's/\r$//' ./mvnw

# Rendre le script Maven Wrapper exécutable
RUN chmod +x mvnw

# Préparer l'environnement Maven Wrapper et télécharger les dépendances
RUN ./mvnw dependency:go-offline

# Copier le reste des sources de l'application
COPY src/ src/

# Exposer le port 8080
EXPOSE 8080

# Commande par défaut pour démarrer l'application avec des modifications à chaud
CMD ["./mvnw", "spring-boot:run"]
