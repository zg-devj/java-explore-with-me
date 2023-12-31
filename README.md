# Explore With Me

![](https://img.shields.io/github/languages/count/zg-devj/java-explore-with-me)
![](https://img.shields.io/github/languages/code-size/zg-devj/java-explore-with-me)

### Описание
Приложение ExploreWithMe (англ. «исследуй со мной») позволит пользователям делиться информацией об интересных событиях и находить компанию для участия в них.

### Стек
Java 11, REST API, SpringBook, Maven, JPA, Lombok, PostgreSQL, Docker

### Администрирование локаций

> https://github.com/zg-devj/java-explore-with-me/pull/5

* Администратор
    - может добавить локацию, статус у локации будет Published
    - может изменить статус локации добавленной пользователем на (Published, Rejected) из Pending
    - может удалить локацию со статусом Rejected
* Пользователь
    - может создать локацию в состоянии Pending (Для последующего одобрения/отказа администратором
* Все
    - могут получить все опубликованные локации
    - возможность найти опубликованные события в опубликованной локации
