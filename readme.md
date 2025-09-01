# 2min — сервис заметок, задач и календаря

**2min** — это веб‑приложение для управления заметками, задачами и календарными событиями для повышения проду.  
Приложение поддерживает регистрацию пользователей, аутентификацию через JWT, работу с календарём и список задач.  
Frontend обслуживается через **Nginx**, backend реализован на **Spring Boot**, данные хранятся в **PostgreSQL**.

---

## 📌 Основные возможности

- 🔑 **Аутентификация и регистрация**
  - Вход с использованием логина и пароля.
  - Регистрация нового пользователя.
  - Получение JWT‑токена для доступа к API.

- 👤 **Управление пользователями**
  - Создание, обновление и удаление пользователей.
  - Получение текущего пользователя, поиск по username и email.
  - Получение списка всех пользователей.

- 🗓️ **Календарь**
  - CRUD‑операции с событиями.
  - Получение всех событий пользователя.
  - Обновление и удаление событий.

- ✅ **Задачи**
  - CRUD‑операции с задачами.
  - Получение активных и запланированных задач.
  - Очистка истории задач за указанный период.

---

## 🛠️ Технологический стек

- **Backend**: Java 17, Spring Boot 3.5  
  (Spring Web, Spring Security, Spring Data JPA, Validation, Liquibase, MapStruct, Lombok, JWT)
- **Database**: PostgreSQL 16
- **Frontend**: Nginx (раздача статических файлов)
- **Сборка**: Gradle 8.7
- **Docker**: мультистейдж сборка (backend, postgres, nginx)

---

## 🚀 Запуск проекта

### 1. Клонирование репозитория
```bash
git clone https://github.com/DKowalski25/2minutes.git
cd 2minutes
```

### 2. Переменные окружения
Создайте файл **.env** в корне проекта:
```env
DB_NAME=name
DB_USERNAME=username
DB_PASSWORD=password
POSTGRESQL_URL=jdbc:postgresql://localhost:5433/name

JWT_SECRET=your_secret_key
```

### 3. Сборка базового образа
```bash
docker build -t gradle-base:8.7 -f dockerfile.base .
```

### 4. Сборка и запуск проекта
```bash
docker compose up --build
```

### 5. Доступ к сервисам
- **Backend API** → [http://localhost:8080/api](http://localhost:8080/api)  
- **Frontend (Nginx)** → [http://localhost](http://localhost)  
- **Postgres** → `localhost:5433`  

---

## 📂 Структура проекта

```
src/
└── main/
    └── java/
        └── com.github.DKowalski25._min/
            ├── 📁 config/                    # Конфигурационные классы
            │   └── 📁 security/              # Настройки безопасности
            │       ├── 📁 jwt/               # JWT утилиты
            │       │   ├── JwtFilter         # Фильтр для JWT
            │       │   └── JwtUtil           # Утилиты работы с JWT
            │       ├── 📁 services/
            │       │   └── CustomUserDetailsService # Кастомный UserDetailsService
            │       ├── SecurityConfig        # Конфиг Spring Security
            │       └── WebConfig             # Web конфигурация
            │
            ├── 📁 controller/                # REST контроллеры
            │   ├── auth/                     # Аутентификация
            │   ├── calendar/                 # Календарь событий
            │   ├── exception/                # Обработка исключений
            │   ├── task/                     # Управление задачами
            │   └── user/                     # Управление пользователями
            │
            ├── 📁 db.seeds/                  # Наполнение БД начальными данными
            │
            ├── 📁 dto/                       # Data Transfer Objects
            │   ├── auth/                     # DTO для аутентификации
            │   ├── calendar/                 # DTO для календаря
            │   ├── task/                     # DTO для задач
            │   ├── taskblock/                # DTO для блоков задач
            │   ├── user/                     # DTO для пользователей
            │   ├── ErrorResponse             # DTO для ошибок
            │   └── ValidationErrorDTO        # DTO для ошибок валидации
            │
            ├── 📁 exceptions/                # Кастомные исключения
            │
            ├── 📁 models/                    # Сущности БД (Entity)
            │
            ├── 📁 repository/                # Репозитории (Data Access Layer)
            │   ├── calendar/                 # Репозиторий календаря
            │   ├── task/                     # Репозиторий задач
            │   ├── timeblock/                # Репозиторий временных блоков
            │   └── user/                     # Репозиторий пользователей
            │
            ├── 📁 scheduler/                 # Планировщики задач
            │
            ├── 📁 service/                   # Сервисный слой (бизнес-логика)
            │
            ├── 📁 util/                      # Утилитарные классы
            │
            └── Application.java              # Главный класс приложения
```

---

## 📑 Примеры API

### 🔐 Авторизация
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "user1",
  "password": "password"
}
```

Ответ:
```json
{
  "token": "jwt_token_here"
}
```

### 📝 Создание задачи
```http
POST /api/v1/tasks
Authorization: Bearer jwt_token
Content-Type: application/json

{
  "title": "Сделать отчёт",
  "description": "Подготовить отчёт по проекту",
  "deadline": "2025-09-15T10:00:00"
}
```

---

## 🧪 Тестирование

```bash
./gradlew test
```

Используемые библиотеки для тестов:
- JUnit 5
- Spring Boot Test
- Mockito
- AssertJ
---

# 📈 Будущие доработки

*   [ ] **Приведение в порядок fronend
*   [ ] **Тесты
*   [ ] **Расширение возможностей юзера через систему уровней юзера
*   [ ] **Добовления админа
*   [ ] **Интеграция с внешними календарями** (Google Calendar, Outlook)
*   [ ] **Мобильное приложение** на React Native
*   [ ] **Уведомления** (email, push-уведомления)
*   [ ] **Расширенные возможности повторения событий**
*   [ ] **Категории и теги для задач**
*   [ ] **Экспорт/импорт данных**
*   [ ] **Аналитика и отчеты** по продуктивности
