## Overview
This is a backend middleware for Figorous. It is designed for universities, scholarship providers, and students to interact securely and efficiently.

## Overall Software Stack
- **Language:** Java
- **Framework:** Spring Boot
- **Database:** MongoDB (via Spring Data MongoDB)
- **Authentication:** JWT, OAuth2 (Google, etc.)
- **Email:** Postmark API (via EmailService)
- **Testing:** JUnit 5, Mockito, Spring Boot Test, MockMvc

## Key Supporting Packages and Libraries
The following dependencies are defined in `pom.xml` (with versions):
- **Spring Boot Starter Security**
    - `org.springframework.boot:spring-boot-starter-security` (Spring Boot Parent: 3.2.6)
- **Spring Security Test**
    - `org.springframework.security:spring-security-test` (Spring Boot Parent: 3.2.6)
- **Spring Boot Starter Web**
    - `org.springframework.boot:spring-boot-starter-web` (Spring Boot Parent: 3.2.6)
- **Spring Boot Starter Test**
    - `org.springframework.boot:spring-boot-starter-test` (Spring Boot Parent: 3.2.6)
- **Lombok**
    - `org.projectlombok:lombok` (provided)
- **MapStruct** (object to DTO mapper)
    - `org.mapstruct:mapstruct:1.5.5.Final`
- **MapStruct Processor**
    - `org.mapstruct:mapstruct-processor:1.5.5.Final` (provided)
- **Spring Boot Starter Data MongoDB**
    - `org.springframework.boot:spring-boot-starter-data-mongodb` (Spring Boot Parent: 3.2.6)
- **Spring Boot Starter Validation**
    - `org.springframework.boot:spring-boot-starter-validation` (Spring Boot Parent: 3.2.6)
- **JJWT (Java JWT)**
    - `io.jsonwebtoken:jjwt-api:0.11.5`
    - `io.jsonwebtoken:jjwt-impl:0.11.5` (runtime)
    - `io.jsonwebtoken:jjwt-jackson:0.11.5` (runtime)
- **Spring Boot Starter OAuth2 Client**
    - `org.springframework.boot:spring-boot-starter-oauth2-client` (Spring Boot Parent: 3.2.6)
- **Spring Security OAuth2 Jose**
    - `org.springframework.security:spring-security-oauth2-jose` (Spring Boot Parent: 3.2.6)
- **Spring Boot DevTools**
    - `org.springframework.boot:spring-boot-devtools` (runtime, optional, Spring Boot Parent: 3.2.6)
- **Spring Dotenv**
    - `me.paulschwarz:spring-dotenv:2.5.4`
- **Spring Boot Starter Mail**
    - `org.springframework.boot:spring-boot-starter-mail` (Spring Boot Parent: 3.2.6)
- **Postmark Java SDK**
    - `com.postmarkapp:postmark:1.11.1`
- **Mockito Core**
    - `org.mockito:mockito-core:5.2.0` (test)

## Main Features
- User registration and authentication (JWT, OAuth2)
- Student application management
- Grant program and eligibility criteria management
- Provider and staff management
- Messaging system
- Contact/inquiry form with email notification
- Role-based access control

## How to Run
1. **MongoDB**: Ensure MongoDB is running and accessible.
2. **Build**: `./mvnw clean install`
3. **Run**: `./mvnw spring-boot:run`
4. **Test**: `./mvnw test`
