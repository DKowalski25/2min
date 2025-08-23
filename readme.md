для сборки базового образа
docker build -t temurin17-unzip -f Dockerfile.base .

Остановить и удалить все контейнеры
docker stop $(docker ps -aq)
docker rm $(docker ps -aq)

удалить все тома
docker volume rm $(docker volume ls -q)

Удалить все образы
docker rmi -f $(docker images -aq)

Удаляет мой образ основной
docker rmi gradle-base:8.7

Очистить кеш сборки Docker
docker builder prune -af

docker build -f Dockerfile.base -t gradle-base:8.7 .
•	-f dockerfile.base — путь к твоему Dockerfile base.
•	-t gradle-base:8.7 — даём имя и тег образу, чтобы потом использовать в FROM.


