# Build container con Maven
FROM maven:3-openjdk-17-slim AS builder
COPY . /app
WORKDIR /app
RUN mvn clean install -DskipTests

# Runtime container con Java
FROM openjdk:17-slim
WORKDIR /app

# Copia el jar
COPY --from=builder /app/target/key-backend-service.jar ./app.jar
COPY src/main/resources/firebase-service-account.json ./firebase-service-account.json

# Usa el path directo en tu código Java o pasa por variable de entorno
ENV FIREBASE_CONFIG_PATH=/app/firebase-service-account.json

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]
