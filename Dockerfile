FROM maven:3.8.5-openjdk-17 as build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/task-management-service-0.1.0-SNAPSHOT.jar /app/task-management-service.jar

EXPOSE 4343

ENTRYPOINT ["java", "-jar", "task-management-service.jar"]