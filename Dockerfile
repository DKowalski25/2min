# Этап сборки
FROM eclipse-temurin:17-jdk as builder
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test  # Собираем JAR (пропускаем тесты)

# Этап запуска
FROM eclipse-temurin:17-jre
WORKDIR /app
 # Копируем JAR
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
 # Запускаем встроенный сервер
CMD ["java", "-jar", "app.jar"]