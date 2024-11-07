# 基础镜像改为官方的 Docker Hub 镜像
FROM maven:3.8.6-openjdk-17 AS build

# 设置工作目录
WORKDIR /app

# 将源代码拷贝到容器中
COPY . /app

# 使用 Maven 构建项目并生成 JAR 文件
RUN mvn clean package -DskipTests

# 使用 OpenJDK 作为运行时环境
FROM openjdk:17-jdk-alpine

# 从构建阶段拷贝生成的 JAR 文件
COPY --from=build /app/target/myapp.jar /app/myapp.jar

# 公开服务端口
EXPOSE 8080

# 启动应用
CMD ["java", "-jar", "/app/myapp.jar"]
