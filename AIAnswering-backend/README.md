# AIAnswering-backend

> Author：[Steven Cui](https://github.com/Stevenucd)

## Features 

### Frameworks & Features

- Spring Boot 2.7.x (2.7.2)
- Spring MVC
- MyBatis + MyBatis Plus (Data Access)
- Spring Boot Debugging Tools and Project Processors
- Spring AOP (Aspect-Oriented Programming, Improve code reusability)
- Spring Scheduler (Scheduled Task)
- Spring Transaction Annotation

### Data Storage

- MySQL Database
- Redis in-memory database
- Tencent Cloud COS Object Storage

## Functions

- Sample SQL provided
- User login, registration, cancellation, update, search, auth management
- Post creation, deletion, editing, updating, database search
- Post likes, unlikes
- Post favourites, unfavourites

### Unit Test

- JUnit5 Unit Test
- Sample Unit Test Classes

### MySQL Database

1）Modify the database configuration of `application.yml` to your own:

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/AIAnswering
    username: root
    password: 123456
```

2）Execute the database statement in `sql/create_table.sql` to automatically create the table.

3）Execute the database statement in `sql/init_data.sql` to automatically initialize the data.