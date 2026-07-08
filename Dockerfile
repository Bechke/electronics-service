FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY build/libs/electronics-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Dreactor.tools.agent.enabled=false", "-jar", "app.jar"]
