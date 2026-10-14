# Backend для платформы объявлений (Avito Clone)

RESTful API для платформы размещения объявлений. Проект реализует полный цикл взаимодействия пользователей с платформой: от регистрации и аутентификации до создания объявлений, загрузки изображений и управления комментариями с четким разграничением прав доступа (USER/ADMIN).

## 🛠 Стек технологий

- **Java 21**
- **Spring Boot 4.1.1** (Spring Web, Spring Data JPA, Spring Security)
- **PostgreSQL** (реляционная база данных)
- **MapStruct 1.5.5.Final** (маппинг DTO ↔ Entity)
- **Lombok 1.18.30** (сокращение шаблонного кода)
- **Springdoc OpenAPI 2.6.0** (Swagger UI для документации API)
- **Maven** (сборка и управление зависимостями)

## 🚀 Как запустить проект

### 1. Требования
- Установленная **JDK 21** или выше
- Установленный **Maven**
- Запущенный сервер **PostgreSQL**

### 2. Настройка базы данных
Создайте базу данных в PostgreSQL (например, с именем `avito_test`):
```sql
CREATE DATABASE avito_test;
```

### 3. Настройка и конфигурация
Приложение настроено на работу с локальной базой данных PostgreSQL. Основные параметры конфигурации (`src/main/resources/application.properties`):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/avito_test
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false
app.images.dir=target/test-images
```

*Обратите внимание: параметр `ddl-auto=create-drop` автоматически очищает и пересоздает структуру таблиц при каждом перезапуске приложения.*

### 4. Сборка и запуск
Так как пароль от базы данных скрыт в переменную окружения `${DB_PASSWORD}`, вы можете передать его напрямую при запуске проекта.

Выполните следующие команды в терминале в корневой папке проекта:

```bash
# Сборка проекта
mvn clean package

# Запуск с передачей пароля от вашей локальной PostgreSQL (замените my_secret_pass на свой пароль)
mvn spring-boot:run -Dspring-boot.run.arguments="--DB_PASSWORD=my_secret_pass"
```
