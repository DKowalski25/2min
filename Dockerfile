# Этап сборки
FROM eclipse-temurin:17-jdk as builder
WORKDIR /app
COPY . .
# Копируем .env файл
COPY .env .
RUN ./gradlew clean build -x test

# Этап запуска
FROM eclipse-temurin:17-jre
WORKDIR /app
# Создаем директорию для логов
RUN mkdir -p /app/logs
# Копируем JAR и .env
COPY --from=builder /app/build/libs/*.jar app.jar
COPY --from=builder /app/.env .
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]