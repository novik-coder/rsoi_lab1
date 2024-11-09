
FROM maven:3.9.0 as build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean install

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/person-api-1.0-SNAPSHOT.jar /app/myapp.jar

ENV DB_USERNAME=sxt_user
ENV DB_PASSWORD=XeoxAOlVSsU5nOZwLQHMsMGOFZq4rEG0

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]
