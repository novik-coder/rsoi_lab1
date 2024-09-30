



FROM maven:3.8.6-eclipse-temurin-17 as builder
WORKDIR /opt/app
COPY mvnw pom.xml ./
COPY ./src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/app
EXPOSE 8080
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]


#FROM maven:3.8.4-openjdk-17 AS build
#WORKDIR /app
#COPY ./pom.xml .
#COPY ./src ./src
#RUN mvn clean package -DskipTests
#
#
#FROM openjdk:17-jdk-alpine
#WORKDIR /app
#COPY --from=build /app/target/your-app-name.jar /app/app.jar
#EXPOSE 8080
#CMD ["java", "-jar", "/app/app.jar"]




