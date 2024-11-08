# 使用 Maven 官方镜像进行构建
FROM maven:3.9.0 as build

# 设置工作目录
WORKDIR /app

# 复制 pom.xml 和源代码
COPY pom.xml .
COPY src ./src

# 构建项目，生成 JAR 文件
RUN mvn clean install

# 使用 OpenJDK 镜像作为运行时镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制构建好的 JAR 文件
# 确保目标路径和文件名正确
COPY --from=build /app/target/person-api-1.0-SNAPSHOT.jar /app/myapp.jar

# 暴露端口
EXPOSE 8080

# 启动应用
CMD ["java", "-jar", "myapp.jar"]
