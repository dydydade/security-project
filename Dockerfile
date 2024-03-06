FROM openjdk:17.0.2-jdk-slim-buster AS builder

WORKDIR /app
COPY gradlew build.gradle ./
COPY gradle ./gradle
COPY src/main ./src/main
RUN ./gradlew bootJar

FROM openjdk:17.0.2-slim-buster

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

ENV PROFILE="dev"

ENTRYPOINT java -jar app.jar --spring.profiles.active=$PROFILE