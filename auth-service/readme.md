# Auth Service

A Spring Boot 3.2 based **authentication service** integrated with **Keycloak** using OAuth2 / OIDC.

Provides:

* OAuth2 login via Keycloak
* User info endpoint (`/me`)
* Health check endpoint (`/public/health`)
* Simple root page showing logged-in user

---

## Table of Contents

* [Prerequisites](#prerequisites)
* [Project Structure](#project-structure)
* [Configuration](#configuration)
* [Running the Application](#running-the-application)
* [Endpoints](#endpoints)
* [Notes](#notes)

---

## Prerequisites

* Java 21
* Maven 3.8+
* Keycloak instance running (or any OAuth2/OpenID Connect provider)
* PostgreSQL (optional, only if you extend with JPA persistence)

---

## Project Structure

```
auth-service/
├─ src/main/java/com/dataflux/auth/
│  ├─ config/
│  │  └─ SecurityConfig.java          # Spring Security + OAuth2 configuration
│  ├─ controller/
│  │  └─ TestController.java          # Health & User info endpoints
│  └─ AuthServiceApplication.java     # Main Spring Boot application
├─ pom.xml
└─ README.md
```

---

## Configuration

Add your Keycloak settings in `application.yml` or `application.properties`:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          keycloak:
            client-id: auth-service
            client-secret: <YOUR_CLIENT_SECRET>
            scope: openid, profile, email
            authorization-grant-type: authorization_code
            redirect-uri: "{baseUrl}/login/oauth2/code/{registrationId}"
        provider:
          keycloak:
            issuer-uri: http://localhost:8080/realms/<YOUR_REALM>
server:
  port: 8081
```

> Replace `<YOUR_CLIENT_SECRET>` and `<YOUR_REALM>` with your Keycloak values.

---

## Running the Application

**Build and run:**

```bash
mvn clean install
mvn spring-boot:run
```

**Access the service:**

* Health check: [http://localhost:8081/public/health](http://localhost:8081/public/health)
* Login page: [http://localhost:8081/](http://localhost:8081/) → redirected to Keycloak
* User info: [http://localhost:8081/me](http://localhost:8081/me) → returns OIDC claims

---

## Endpoints

| Endpoint         | Method | Description                     | Auth Required |
| ---------------- | ------ | ------------------------------- | ------------- |
| `/public/health` | GET    | Returns service health status   | No            |
| `/me`            | GET    | Returns logged-in user claims   | Yes           |
| `/`              | GET    | Welcome page showing user email | Yes           |

---

## Notes

* `oauth2Login()` is used for Keycloak login. If using Spring Security 6.1+, check for **deprecated warnings** and follow migration guides.
* You can extend this service to **act as a resource server** by adding JWT validation (`spring-boot-starter-oauth2-resource-server`).
* The `/me` endpoint demonstrates **accessing OIDC claims** like email and name.
* Customize the `SecurityConfig` for **role-based access** if required.

---

## References

* [Spring Security OAuth2 Client](https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html)
* [Keycloak Documentation](https://www.keycloak.org/documentation)

## Security flow
         +------------------+
         |   Client (UI)    |
         +------------------+
                  |
                  | HTTP request to /me
                  v
         +------------------+
         |   Auth Service   |
         | (Spring Boot)    |
         +------------------+
                  |
                  | OAuth2 / OIDC
                  v
         +------------------+
         |    Keycloak      |
         | (Identity Server)|
         +------------------+
