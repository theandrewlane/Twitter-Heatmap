#CS 4230 - Group Project
<hr>

####This project uses [Spring Boot](https://projects.spring.io/spring-boot/) and the [Thymeleaf template engine](http://www.thymeleaf.org/)

###Documents
- [Group Contract](./docs/group-contract.md)

###Links
- [Slack](cs4230hq.slack.com) (repository integration coming soon)
- Travis CI (repository integration coming soon)

###Setup 
Note: Uses Tomcat Embedded

1. Install [Maven](https://maven.apache.org/download.cgi) (intellij should have it already)
2. Enable Maven plugins via IntelliJ Plugin Settings
    - Maven Integration
    - Maven Integration Plugin
3. Enable Spring plugins via IntelliJ Plugin Settings (everything)

#####If you clone this repository through IntelliJ, it should add the run configuration for you, if not, you can get things running following these steps:
1. Right click on ```pom.xml``` and select "Add as Maven Project" - You'll also want to enable auto-import
2. Run ```mvn install``` from a terminal - this will download dependencies, build the app, and start Tomcat (embedded)
3. You should now be able to access the Spring App @[http://localhost:8080/hello.html](http://localhost:8080/hello.html)
