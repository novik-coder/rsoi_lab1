
FROM maven:3.9.0 as build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean install

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/person-api-1.0-SNAPSHOT.jar /app/myapp.jar

EXPOSE 8080

CMD ["java", "-jar", "myapp.jar"]
