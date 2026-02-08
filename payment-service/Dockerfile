# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# 1. Copiamos el POM PADRE
COPY pom.xml .

# 2. Copiamos los POMs de TODOS los módulos (para que Maven valide la estructura)
COPY payment-service/pom.xml payment-service/
COPY rewards-service/pom.xml rewards-service/

# 3. Descargamos dependencias enfocados en Payment
RUN mvn -pl payment-service -am dependency:go-offline

# 4. Copiamos el código y compilamos
COPY payment-service/src payment-service/src
RUN mvn -pl payment-service clean package -DskipTests

# Stage 2: Runtime (Se mantiene igual)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/payment-service/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]