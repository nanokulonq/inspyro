# inspyro
Spring Boot RESTful веб-приложение для размещения своих работ и идей для IT проектов.
# Используемые технологии
* Java
* Maven
* Spring Boot, Spring Security
* PostgreSQL, Spring Data JPA
* Docker
* GIT
# Зависимости
* Java 21 JDK
* PostgreSQL Database
* Maven
* Docker
# Установка
* В IntellijIdea или похожей IDE импортирутей maven проект. 
* Сбилдите проект.
* Создайте две базы данных

Для модуля идей:
```shell
docker run --name ideas-db -p 5432:5432 -e POSTGRES_USER=ideas -e POSTGRES_PASSWORD=ideas -e POSTGRES_DB=ideas postgres:16
```
Для модуля пользователей:
```shell
docker run --name user-db -p 5433:5432 -e POSTGRES_USER=user -e POSTGRES_PASSWORD=user -e POSTGRES_DB=user postgres:16
```
