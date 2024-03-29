# Reddit Clone - Backend

 Backend of a Reddit Clone, built with:

 - Spring Boot
 - Spring Security
 - Spring Data JPA
 - PostgreSQL JDBC Driver
 - Lombok
 - Java Mail Sender
 - MapStruct
 - JSON Web Token

You can access the api documentation [here](https://spring-reddit-clone-production.up.railway.app/swagger-ui.html).

You can configure your database, hibernate and email functionality by adding properties to [application.properties](https://github.com/amycardoso/spring-reddit-clone/blob/master/src/main/resources/application.properties).

In this application, users receive account activation emails and comment notification emails, for that reason, we need an SMTP server to send the emails, we can use a fake SMTP server called [MailTrap](https://mailtrap.io/).
