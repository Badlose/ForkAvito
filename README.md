Материалы для выполнения курсовой работы учениками профессии java-разработчик. 

**Исполнитель:**

*   [Соломин Иван](https://github.com/Badlose)
   
1. Описание приложения
Приложение представляет собой RESTful API, предоставляющее функциональность для:

*   Создания, чтения, обновления и удаления объявлений (CRUD).
*   Поиска объявлений по различным критериям (ключевым словам, категориям, ценам и т.д.).
*   Управления пользователями (регистрация, аутентификация, авторизация).
*   Управления изображениями (загрузка, хранение, получение URL).
*   Возможность добавления комментариев к объявлениям.
*   Приложение спроектировано для обеспечения высокой производительности, масштабируемости и безопасности.

2. Технологии и зависимости
*   Язык программирования: Java
*   Фреймворки: Spring Boot, Spring Data JPA, Spring Security
*   База данных: PostgreSQL, H2
*   ORM: Hibernate
*   Docker: Test containers
*   API: REST (Spring Web)   

3. Установка и запуск

1.  Клонируйте репозиторий: `git clone https://github.com/Badlose/MyDiploma`
2.  Соберите JAR-архив: `mvn clean install`

JAR-архив будет создан в директории `target`.

## Run Instructions

1.  Установите необходимые переменные среды (см. раздел "Configuration").
2.  Запустите Docker: `docker run -p 3000:3000 --rm ghcr.io/dmitry-bizin/front-react-avito:v1.21`
3.  Запустите приложение: `java -jar target/your-project.jar`


## Configuration

Необходимые переменные среды Spring Boot:

*   `SPRING_DATASOURCE_URL`: URL базы данных PostgreSQL (например, `jdbc:postgresql://localhost:5432/your_database`)
*   `SPRING_DATASOURCE_USERNAME`: Имя пользователя базы данных
*   `SPRING_DATASOURCE_PASSWORD`: Пароль пользователя базы данных
