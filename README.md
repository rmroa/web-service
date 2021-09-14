<h1>Project name - "news-management-system"</h1>

<h2>Technologies used in project: PostgreSQL, JPA(Hibernate), SpringBoot(Data, Rest), Mapstruct, Flyway, JUnit 5.</h2>

<h3>The project includes (main parts):</h3>

- ORM (vendor adapter - Hibernate). _[ORM](https://github.com/rmroa/web-service/tree/master/src/main/java/com/rm/springboot/project/entity)
- DTO ( _[DTO](https://github.com/rmroa/web-service/tree/master/src/main/java/com/rm/springboot/project/dto). For conversion was use MapStruct, 
realization in code: _[Mapper](https://github.com/rmroa/web-service/tree/master/src/main/java/com/rm/springboot/project/mapper))
- JPA (vendor adapter - Hibernate + covered by Spring Data JPA)
    + _[Repository](https://github.com/rmroa/web-service/tree/master/src/main/java/com/rm/springboot/project/repository) (JPA wrappers, Pageable with Sort)
    + _[Service](https://github.com/rmroa/web-service/tree/master/src/main/java/com/rm/springboot/project/service) (most of the non-primitive logic locate in _[service.impl](https://github.com/rmroa/web-service/tree/master/src/main/java/com/rm/springboot/project/service/impl))
    
- _[Controllers](https://github.com/rmroa/web-service/tree/master/src/main/java/com/rm/springboot/project/controller)

<h4>Another module:</h4>

- _[Testing](https://github.com/rmroa/web-service/tree/master/src/test/java/com/rm/springboot/project/service/impl) (JUnit 5)

- _[FlyWay](https://github.com/rmroa/web-service/tree/master/src/main/resources/db/migration)

- _[AOP](https://github.com/rmroa/web-service/tree/master/src/main/java/com/rm/springboot/project/aop)

<h4>Base settings:</h4>

- _[applications.yml](https://github.com/rmroa/web-service/blob/master/src/main/resources/application.yml)

<h4>Build project:</h4>

- _[gradle](https://github.com/rmroa/web-service/blob/master/build.gradle)
