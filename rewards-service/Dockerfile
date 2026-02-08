# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

COPY pom.xml .
COPY payment-service/pom.xml payment-service/
COPY rewards-service/pom.xml rewards-service/

RUN mvn -pl rewards-service -am dependency:go-offline

COPY rewards-service/src rewards-service/src
RUN mvn -pl rewards-service clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/rewards-service/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]