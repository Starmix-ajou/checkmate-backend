# 1단계: Gradle을 사용해 빌드 (ARM64 지원)
FROM --platform=linux/arm64 gradle:8.7-jdk21 AS builder
WORKDIR /app

# 프로젝트 파일 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src
RUN chmod +x gradlew

# 애플리케이션 빌드 (테스트 제외)
RUN ./gradlew clean build -x test

FROM --platform=linux/arm64 eclipse-temurin:21-jre-alpine
WORKDIR /app

# 빌드된 JAR 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]