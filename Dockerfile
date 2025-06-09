# Java 17�� ���� ��ġ�� Docker ���̽� �̹���
FROM eclipse-temurin:17-jdk

# �� ���丮 ����
WORKDIR /app

# ���� ������Ʈ �ڵ� ���� ����
COPY . .

# gradlew ���� ���� �߰� (�ſ� �߿�!!)
RUN chmod +x gradlew

# ���� ����
RUN ./gradlew build

# ���� ���� (JAR ���� �̸� ��Ȯ�� ���߱�!)
CMD ["java", "-jar", "build/libs/backup-0.0.1-SNAPSHOT.jar"]
