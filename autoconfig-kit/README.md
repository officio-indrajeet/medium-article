# Spring Boot auto-configuration demo

A tiny custom auto-configuration (`greeting-spring-boot-starter`) and an app that uses it (`demo-app`).
Companion code for the Medium article "Spring Boot Auto-Configuration Explained: How Beans Are Created Automatically".

## Requirements
Java 21+, Maven 3.9+ (built with Spring Boot 3.3.5).

## Build
    mvn clean install

## Run the three experiments
    java -jar demo-app/target/demo-app-1.0.0.jar                           # 1. default bean
    java -jar demo-app/target/demo-app-1.0.0.jar --demo.custom=true        # 2. your own bean wins
    java -jar demo-app/target/demo-app-1.0.0.jar --greeting.enabled=false  # 3. disabled (startup fails on purpose)

`debug=true` in `demo-app/src/main/resources/application.properties` prints the conditions report.
