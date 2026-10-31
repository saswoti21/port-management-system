# Port Management System

A backend application developed using Java and Spring Boot to manage port-related operations through RESTful APIs.

## Project Overview

The Port Management System is designed to organize and manage port operations, including ports, vessels, berths, and users.

The application follows a layered architecture using controllers, services, repositories, entities, and DTOs.

**Project Status:** Work in Progress

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Spring Security
- JWT Authentication
- MySQL
- Maven
- Swagger / OpenAPI
- Lombok
- Eclipse IDE

## Features

### Authentication and Authorization
- User registration and login
- Password encryption using BCrypt
- JWT-based authentication
- Spring Security integration

### Port Management
- Create and retrieve port records
- Retrieve port details by ID
- Pagination and sorting
- Duplicate port-code validation
- Centralized exception handling

### Additional Modules
- User Management
- Vessel Management
- Berth Management

These modules are under development.

## Project Architecture

The application follows a layered architecture:

- **Controller Layer:** Handles incoming HTTP requests.
- **Service Layer:** Contains business logic.
- **Repository Layer:** Communicates with the database using Spring Data JPA.
- **Entity Layer:** Represents database tables.
- **DTO Layer:** Transfers data between application layers.
- **Security Layer:** Handles JWT authentication and authorization.

## Database

MySQL is used as the relational database.

Database configuration is maintained in `application.properties`. Sensitive credentials should be supplied through environment variables rather than committed to GitHub.

## API Documentation

Swagger UI is integrated for API documentation and testing.

After starting the application, access Swagger UI at:

`http://localhost:8081/swagger-ui/index.html`

## How to Run the Project

1. Clone the repository:

   `git clone https://github.com/saswoti21/port-management-system.git`

2. Open the project in Eclipse IDE as an existing Maven project.

3. Configure your MySQL database.

4. Set the required environment variables for database credentials and JWT configuration.

5. Run the Spring Boot application.

6. Open Swagger UI to explore and test the APIs.

## GitHub Repository

https://github.com/saswoti21/port-management-system

---

**Note:** This project is actively being developed. Additional features and API improvements will be added as development progresses.
