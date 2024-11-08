# 使用官方 Maven 镜像构建应用
FROM maven:3.9.9-jdk-22 as build

# 设置工作目录
WORKDIR /app

# 复制 Maven 构建文件并安装依赖
COPY pom.xml /app/
RUN mvn dependency:go-offline

# 复制源代码并构建项目
COPY src /app/src/
RUN mvn clean package -DskipTests

# 第二阶段：创建最终镜像
FROM openjdk:22-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制构建好的 JAR 文件
COPY --from=build /app/target/myapp.jar /app/app.jar

# 暴露端口
EXPOSE 8080

# 启动应用
CMD ["java", "-jar", "/app/app.jar"]
