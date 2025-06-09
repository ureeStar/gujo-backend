# Java 17이 사전 설치된 Docker 베이스 이미지
FROM eclipse-temurin:17-jdk

# 앱 디렉토리 설정
WORKDIR /app

# 현재 프로젝트 코드 전부 복사
COPY . .

# gradlew 실행 권한 추가 (매우 중요!!)
RUN chmod +x gradlew

# 빌드 실행
RUN ./gradlew build

# 서버 실행 (JAR 파일 이름 정확히 맞추기!)
CMD ["java", "-jar", "build/libs/backup-0.0.1-SNAPSHOT.jar"]
