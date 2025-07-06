# MVN container
FROM maven:3-openjdk-17-slim AS builder
COPY . /app
WORKDIR /app
RUN mvn clean install -DskipTests

# JAVA container
FROM openjdk:17-slim
WORKDIR /app
COPY --from=builder /app/target/* ./app/
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=dev","./app/key-backend-service.jar"]