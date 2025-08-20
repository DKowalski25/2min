# Этап сборки
FROM temurin17-unzip AS builder
WORKDIR /app

# Копируем Gradle wrapper и делаем его исполняемым
COPY gradlew .
COPY gradle ./gradle
RUN chmod +x gradlew

# Копируем только файлы зависимостей, чтобы кешировать слой
COPY build.gradle.kts settings.gradle.kts ./

# Скачиваем зависимости один раз
RUN ./gradlew build -x test --no-daemon --parallel --build-cache

# Копируем весь исходный код
COPY . .

# Собираем jar без clean
RUN ./gradlew build -x test --no-daemon --parallel --build-cache

# Этап запуска
FROM eclipse-temurin:17-jre
WORKDIR /app
RUN mkdir -p /app/logs

COPY --from=builder /app/build/libs/*.jar app.jar
COPY --from=builder /app/.env .

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]