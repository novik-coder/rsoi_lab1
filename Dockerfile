WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean install

FROM openjdk:17-jdk-slim

WORKDIR /app

# 使用 JRE 运行时环境作为运行镜像
FROM openjdk:17-alpine
USER root
# 设置工作目录
WORKDIR /app
# 从 builder 镜像中复制构建好的 JAR 文件到运行时镜像
COPY ./app.jar /app/app.jar
COPY ./config.json /app/config.json
COPY ./config.json /config.json


EXPOSE 8080

ENTRYPOINT ["java", "-jar", "myapp.jar"]
