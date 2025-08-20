FROM temurin17-unzip AS builder
WORKDIR /app

COPY gradlew .
COPY gradle ./gradle
RUN chmod +x gradlew

COPY build.gradle.kts settings.gradle.kts ./
RUN ./gradlew build -x test --no-daemon --parallel --build-cache || true

COPY . .
RUN ./gradlew clean build -x test --no-daemon --parallel --build-cache

FROM eclipse-temurin:17-jre
WORKDIR /app
RUN mkdir -p /app/logs

COPY --from=builder /app/build/libs/*.jar app.jar
COPY --from=builder /app/.env .

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]