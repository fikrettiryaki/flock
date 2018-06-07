# How to Compile And Run
- The project is a Spring-Boot project. 
    - It can compiled with Gradle **gradlew build**, and then the produced jar file can be run with the comman **java -jar build/libs/farmshop-0.1.0.jar**
    - Alternatively for testing purposes using gradle via **gradlew bootRun** will run the application
- After running the server, the services can be consumed at the following adress:
      http://localhost:8080/farmshop
   - DB desing and values are accessable via this link:
      http://localhost:8080/h2/
      jdbc url: jdbc:h2:mem:farmshop_db
   - Rest services can be tested via this link:
      http://localhost:8080/swagger-ui.html
    
# Key Design Decision & Assumptions
- The location of xml resource can be configured via application.yml with the field xml.path
- Default port is 8080
- Validation rules are only a demonstation of possible implementations. 
- It is assumed that the date of stock and orders are not taken into consideration. In ideal cases  wool or milk values present should be changed in a timely manner.
- 


# Tests
Application can be tested via postman or any other tool that can be used to construct HTTP REST requests.

# Improvement points
Validations can be richer
Stock information could have a date component where orders and stocks are matched in a daily manner
integration tests can be added


