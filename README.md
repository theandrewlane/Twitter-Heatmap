#CS 4230 - Group Project
<hr>

####This project uses [Spring Boot](https://projects.spring.io/spring-boot/) and the [Thymeleaf template engine](http://www.thymeleaf.org/)

###Documents
- [Group Contract](./docs/group-contract.md)

###Links
- [Slack](cs4230hq.slack.com) (repository integration coming soon)
- Travis CI [![Travis](https://img.shields.io/travis/rust-lang/rust.svg?style=plastic)](https://github.com/WeberCS4230/Twitter-Thing) (repository integration coming soon) 

###Setup 
Note: Uses Tomcat Embedded

1. Install [Maven](https://maven.apache.org/download.cgi) (intellij should have it already)
2. Enable Maven plugins via IntelliJ Plugin Settings
    - Maven Integration
    - Maven Integration Plugin
3. Enable Spring plugins via IntelliJ Plugin Settings (everything)
4. Install Live Reload Chrome Plugin

###Run
Note: If you clone this repository through IntelliJ, it should add the run configuration for you

1. Right click on ```pom.xml``` and select "Add as Maven Project" - You'll also want to enable auto-import
2. Run ```mvn install``` from a terminal - this will download dependencies, and package the app (.jar for now)
3. Run ```mvn spring-boot:run``` to start the Spring Boot application OR use the run configuration in IntelliJ
5. You should now be able to access the Spring App @[http://localhost:8080/hello.html](http://localhost:8080/hello.html)
