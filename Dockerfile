# 使用支持 Java 22 的镜像
FROM openjdk:22-jdk-slim as build

# 设置工作目录
WORKDIR /app

# 复制 pom.xml 和源代码
COPY pom.xml .
COPY src ./src

# 构建项目
RUN mvn clean install

# 运行时镜像
FROM openjdk:22-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制 JAR 文件
COPY --from=build /app/target/myapp.jar /app/myapp.jar

# 暴露端口
EXPOSE 8080

# 运行 JAR 文件
CMD ["java", "-jar", "myapp.jar"]
