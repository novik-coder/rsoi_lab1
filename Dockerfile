# 使用官方 Java 镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 拷贝 Maven 构建的 JAR 文件到容器
COPY target/myapp.jar myapp.jar

# 暴露端口
EXPOSE 8080

# 启动应用
CMD ["java", "-jar", "myapp.jar"]
