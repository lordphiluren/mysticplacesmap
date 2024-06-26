# Mystery Guide Backend
**Mystery Guide** - сервис, позволяющий пользователям делиться и обсуждать интересные таинственные места, тем самым способствуя развитию *мистического туризма* в регионах России.

Данный репозиторий содержит *REST API* сервиса **Mystery Guide**.

Репозиторий с **frontend** находится по [ссылке](https://github.com/lordphiluren/mysticplacesfrontend).
## Использованные технологии
- Java 21
- Spring Framework (Spring Boot, Spring Security, Spring Data JPA, Spring Web)
- PostgreSQL
- Liquibase
- Testcontainers, JUnit, Mockito
- AWS SDK, Yandex Object Storage
- Swagger
- Docker - контейнеры, образы, volumes, написание Dockerfile, Docker Compose
- Lombok
- Maven
## Реализованный функционал
- Сервис поддерживает аутентификацию и авторизацию пользователей по логину и паролю.
- Доступ к API аутентифицирован с помощью **JWT** токена.
- Пользователи могут добавлять в систему мистические места, а также управлять ими в последствии.
- Пользователи могут просматривать мистические места, добавленные другими пользователями, добавлять к ним фото, выставлять оценку.
- К местам, добавленым в систему, можно оставлять комментарии с вложениями.
- API позволяет получать список мистических мест с различными фильтрами (по тегам, рейтингу, пользователю, пагинацией).
- Реализована интеграция с **Yandex Object Storage** для хранения вложений.
- Сервис корректно обрабатывает ошибки и возвращает понятные сообщения, а также валидирует входящие данные.
- Сервис задокументирован и настроен **Swagger UI**.
- База данных поднимает с помощью скриптов миграции **Liquibase**.
- Дев среда поднимается с помощью **docker compose**.
- Написаны **юнит тесты** для проверки основных функций системы.
- Написаны интеграционные тесты с помощью **Testcontainers** для провреки системы в целом.
## Инструкция по запуску проекта на локальном компьютере
- Установить [Docker](https://docs.docker.com/get-docker/) на локальную машину
- Склонировать репозиторий
```
git clone https://github.com/lordphiluren/mysticplacesmap
```
- Запустить приложение
```
docker-compose up
```
- Страница с документацией будет доступна по ссылке: http://localhost:8080/swagger-ui/index.html
## Возможности улучшить функционал
- Реализация админской части сервиса
- Добавление кеширования
- Оптимизация запросов к БД