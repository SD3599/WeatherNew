# Simple Weather Application using Spring Boot
The user needs to register in order to use the application.
Once registered the user can request for weather forecast for various places round the world.

The weather forecast details include
 - Currenttemperature (minimum , maximum )
 - Forecast for the day
 
The user can select any location to see weather forecast for that place
 - For # days
 - In degrees celsius or fahrenheit
 
Prerequisites
 - JDK 1.8 or later
 - Maven 3 or later
 - MySQL 5.6 or later
 - Tomcat 8.x or later
 
MySQL

 Name of the database is newdb alternatively to use your database change the following line in application.properties file.
 - spring.datasource.url=jdbc:mysql://localhost:3306/newdb

 The username of database used is "user" and password "password", alternatively these lines in application.properties can be changed.
 - spring.datasource.username=user
 - spring.datasource.password=password

Run
 - mvn spring-boot:run 

URL
 -  http://localhost:8090/
 
 Sources
 -   https://hellokoding.com/registration-and-login-example-with-spring-security-spring-boot-spring-data-jpa-hsql-jsp/
 -   https://github.com/fedy2/yahoo-weather-java-api


