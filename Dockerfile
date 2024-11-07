# 使用 OpenJDK 基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 拷贝 pom.xml 和项目依赖文件
COPY pom.xml .

# 下载依赖并构建项目（会生成 target/ 目录）
RUN mvn clean install

# 拷贝构建的 JAR 文件
COPY target/myapp.jar myapp.jar

# 暴露端口
EXPOSE 8080

# 启动应用
CMD ["java", "-jar", "myapp.jar"]

