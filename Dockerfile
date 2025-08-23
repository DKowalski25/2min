# ==============================
# Этап 1: Builder
# ==============================
FROM gradle-base:8.7 AS builder
WORKDIR /app

# Явно указываем путь к системному Gradle
ENV PATH="/opt/gradle/bin:${PATH}"

# Копируем ТОЛЬКО файлы зависимостей
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Проверяем что Gradle доступен и используем его
RUN gradle --version && \
    gradle build -x test --no-daemon --parallel --build-cache

# Копируем исходный код
COPY src ./src
COPY .env .

# Собираем проект
RUN gradle build -x test --no-daemon --parallel --build-cache

# ==============================
# Этап 2: Run
# ==============================
FROM eclipse-temurin:17-jre
WORKDIR /app

RUN mkdir -p /app/logs
COPY --from=builder /app/build/libs/*.jar app.jar
COPY --from=builder /app/.env .
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]