FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY estoque-de-produtos-crud/pom.xml .
RUN mvn dependency:go-offline

COPY estoque-de-produtos-crud/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

RUN apt-get update && apt-get install -y locales && \
    locale-gen en_US.UTF-8 && \
    update-locale LANG=en_US.UTF-8

ENV LANG=en_US.UTF-8 \
    LANGUAGE=en_US:en \
    LC_ALL=en_US.UTF-8

COPY --from=builder /app/target/estoque-de-produtos-0.0.1-SNAPSHOT.jar app.jar

ENV SPRING_PROFILES_ACTIVE=dev
EXPOSE 8080

ENV _JAVA_OPTIONS="-Dfile.encoding=UTF-8"
ENTRYPOINT ["java", "-jar", "app.jar"]
