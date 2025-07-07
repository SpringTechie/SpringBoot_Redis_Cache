FROM openjdk:23-slim
WORKDIR /app
LABEL authors="springtechie"
COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app/app.jar"]