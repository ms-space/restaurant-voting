# Restaurant voting

Voting system for deciding where to have lunch (without frontend).

* 2 types of users: admin and regular users
* Admin can input a restaurant, and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote on which restaurant they want to have lunch at
* Only one vote counted per user
* If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed
* Each restaurant provides a new menu each day.

-----------------------------------------------------
Tech stack: Maven, Spring MVC, Spring Security, Spring Data JPA, REST (Jackson), JDK 17, <br>
Stream API, H2 (in memory), JUnit5, Swagger v2 (API)

- Run: `mvn spring-boot:run` in root directory.

-----------------------------------------------------
[REST API documentation](http://localhost:8080/)

Login information:

```
Admin: admin@gmail.com / admin
User:  user@gmail.com / password
```