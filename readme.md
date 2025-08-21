для сборки базового образа
docker build -t temurin17-unzip -f Dockerfile.base .

Остановить и удалить все контейнеры
docker stop $(docker ps -aq)
docker rm $(docker ps -aq)

удалить все тома
docker volume rm $(docker volume ls -q)

Удалить все образы
docker rmi -f $(docker images -aq)

Очистить кеш сборки Docker
docker builder prune -af


# Все тесты
./gradlew test

# Только unit тесты
./gradlew unitTest

# Только интеграционные тесты
./gradlew integrationTest

# С конкретным профилем
./gradlew test -Dspring.profiles.active=test
