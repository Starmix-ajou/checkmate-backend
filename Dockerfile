# 1단계: Gradle을 사용해 빌드 (ARM64 지원)
# docker.io/library/를 붙여서 레지스트리 혼동을 방지합니다.
FROM docker.io/library/gradle:8.7-jdk21 AS builder
WORKDIR /app

# 프로젝트 파일 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src
RUN chmod +x gradlew

# 애플리케이션 빌드 (테스트 제외)
RUN ./gradlew clean build -x test

# app.jar로 복사 (가장 큰 용량의 jar를 선택하는 로직, 유용하네요!)
RUN cp $(ls -S /app/build/libs/*.jar | head -n 1) /app/app.jar

# 2단계: 실행용 이미지
# 여기도 docker.io/library/를 붙여주세요!
FROM --platform=linux/arm64 docker.io/library/eclipse-temurin:21-jre-alpine
WORKDIR /app

# 빌드된 JAR 복사
COPY --from=builder /app/app.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-Djava.security.egd=file:/dev/./urandom", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar"]