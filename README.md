# Product API

A RESTful API for product management with JWT authentication and refresh token rotation.

## Features
- Full CRUD for products and items
- JWT authentication with refresh token rotation
- Role-based authorization (ADMIN for write, authenticated for read)
- Pagination, sorting, and input validation
- Global exception handling
- OpenAPI documentation (Swagger)
- Docker and Docker Compose support

## Tech Stack
- Java 17, Spring Boot 3.2
- Spring Security, JWT
- Spring Data JPA, MySQL
- JUnit 5, Mockito, H2 for testing
- Maven, Docker

## Getting Started

### Prerequisites
- Java 17
- Maven
- Docker (optional)

### Running Locally
1. Clone the repository
2. Configure MySQL database (or use Docker)
3. Update `application.yml` with your database credentials
4. Run `mvn clean install`
5. Run `mvn spring-boot:run`

### Running with Docker
```bash
docker-compose up --build