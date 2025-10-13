FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY estoque-de-produtos-crud/pom.xml .
RUN mvn dependency:go-offline

COPY estoque-de-produtos-crud/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=builder /app/target/estoque-de-produtos-0.0.1-SNAPSHOT.jar app.jar

ENV SPRING_PROFILES_ACTIVE=dev
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
