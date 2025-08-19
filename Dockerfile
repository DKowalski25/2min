# Этап сборки
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# 1. Копируем файлы, которые влияют на зависимости
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

# 2. Скачиваем зависимости (кешируется Docker)
RUN ./gradlew dependencies --no-daemon || true

# 3. Копируем gradlew скрипт и исходный код
COPY gradlew .
COPY . .

# 4. Полная сборка проекта
RUN ./gradlew clean build -x test --no-daemon --parallel

# Этап запуска
FROM eclipse-temurin:17-jre
WORKDIR /app
RUN mkdir -p /app/logs

# Копируем JAR и .env
COPY --from=builder /app/build/libs/*.jar app.jar
COPY --from=builder /app/.env .

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]