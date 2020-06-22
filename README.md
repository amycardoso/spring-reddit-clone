# reddit-clone
[![Codeship Status for amycardoso/spring-reddit-clone](https://app.codeship.com/projects/64f96490-9634-0138-56e1-1a526a14ed5f/status?branch=master)](https://app.codeship.com/projects/400570)

 Backend of a Reddit Clone, built with:

 - Spring Boot
 - Spring Security
 - Spring Data JPA
 - PostgreSQL JDBC Driver
 - Lombok
 - Java Mail Sender
 - MapStruct
 - JSON Web Token

You can access the api documentation [here](https://reddit-clone-spring-boot.herokuapp.com/swagger-ui.html).

You can configure your database, hibernate and email functionality by adding properties to [application.properties](https://github.com/amycardoso/spring-reddit-clone/blob/master/src/main/resources/application.properties).

In this application, users receive account activation emails and comment notification emails, for that reason, we need an SMTP server to send the emails, we can use a fake SMTP server called [MailTrap](https://mailtrap.io/).
