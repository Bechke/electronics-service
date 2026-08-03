FROM gradle:8.4.0-jdk21 AS builder
WORKDIR /app
COPY --chown=gradle:gradle . .
RUN chmod +x gradlew && ./gradlew bootJar --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 9196
ENTRYPOINT ["java", "-Dreactor.tools.agent.enabled=false", "-jar", "app.jar"]
