# 使用 Maven 的官方镜像进行构建
FROM maven:3.9.0-openjdk-22 as build

# 设置工作目录
WORKDIR /app

# 复制 pom.xml 和源代码
COPY pom.xml .
COPY src ./src

# 构建项目
RUN mvn clean install

# 使用 openjdk 镜像作为运行时镜像
FROM openjdk:22-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制构建好的 JAR 文件
COPY --from=build /app/target/myapp.jar /app/myapp.jar

# 暴露端口
EXPOSE 8080

# 启动应用
CMD ["java", "-jar", "myapp.jar"]
