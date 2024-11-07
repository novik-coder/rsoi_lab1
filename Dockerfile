# 使用 Maven 和 OpenJDK 作为基础镜像
FROM maven:latest AS build


# 设置工作目录
WORKDIR /app

# 复制项目文件
COPY . .

# 编译项目
RUN mvn clean package -DskipTests

# 运行环境镜像
FROM openjdk:17-jdk-alpine

# 设置工作目录
WORKDIR /app

# 复制 JAR 文件
COPY --from=build /app/target/myapp.jar /app/myapp.jar

# 暴露端口
EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar", "myapp.jar"]
