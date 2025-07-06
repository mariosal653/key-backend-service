# 🏗️ Etapa de construcción con Maven
FROM maven:3-openjdk-17-slim AS builder
COPY . /app
WORKDIR /app
RUN mvn clean install -DskipTests

# 🚀 Etapa de ejecución con Java
FROM openjdk:17-slim
WORKDIR /app

# Copiar el JAR generado
COPY --from=builder /app/target/*.jar /app/key-backend-service.jar

# Copiar también el archivo de Firebase si lo usas en el contenedor
COPY src/main/resources/firebase-service-account.json /app/firebase-service-account.json

# 🔧 Definir variable de entorno para Firebase
ENV FIREBASE_CONFIG_PATH=/app/firebase-service-account.json

# 🔧 Activar perfil de Spring Boot (dev, por ejemplo)
ENV SPRING_PROFILES_ACTIVE=dev

# 🔐 Ejecutar la app
ENTRYPOINT ["java", "-jar", "/app/key-backend-service.jar"]
