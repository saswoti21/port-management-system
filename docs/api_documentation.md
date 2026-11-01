# Port Management System – API Documentation

## Base URL

`http://localhost:8081`

## Authentication

The application uses Spring Security and JWT authentication.

For protected endpoints, include the JWT token in the request header:

`Authorization: Bearer <jwt_token>`

Registration and login are public endpoints.

---

## 1. Authentication APIs

### Register User

- **Method:** POST
- **Endpoint:** `/api/auth/register`
- **Authentication:** Not required
- **Success Status:** 201 Created

Registers a new user.

### Login User

- **Method:** POST
- **Endpoint:** `/api/auth/login`
- **Authentication:** Not required
- **Success Status:** 200 OK

Authenticates a user and returns login information, including the JWT token.

---

## 2. Port Management APIs

All endpoints require JWT authentication.

| Method | Endpoint | Description | Success Status |
|---|---|---|---|
| POST | `/api/ports` | Create a port | 201 Created |
| GET | `/api/ports` | Retrieve all ports | 200 OK |
| GET | `/api/ports/{id}` | Retrieve port by ID | 200 OK |
| PUT | `/api/ports/{id}` | Update port | 200 OK |
| DELETE | `/api/ports/{id}` | Delete port | 200 OK |
| GET | `/api/ports/search?keyword={keyword}` | Search ports | 200 OK |
| GET | `/api/ports/page` | Retrieve paginated ports | 200 OK |

### Pagination and Sorting

**Endpoint:** `GET /api/ports/page`

Query parameters:

| Parameter | Default Value | Description |
|---|---|---|
| page | 0 | Page number |
| size | 10 | Number of records per page |
| sortBy | name | Field used for sorting |
| direction | asc | Sorting direction |

Example:

`GET /api/ports/page?page=0&size=10&sortBy=name&direction=asc`

---

## 3. User Management APIs

All endpoints require JWT authentication.

| Method | Endpoint | Description | Success Status |
|---|---|---|---|
| GET | `/api/users` | Retrieve all users | 200 OK |
| GET | `/api/users/{id}` | Retrieve user by ID | 200 OK |
| PUT | `/api/users/{id}` | Update user details | 200 OK |
| PUT | `/api/users/{id}/activate` | Activate a user | 200 OK |
| DELETE | `/api/users/{id}/deactivate` | Deactivate a user | 200 OK |
| GET | `/api/users/me` | Retrieve current logged-in user | 200 OK |

---

## 4. Vessel Management APIs

All endpoints require JWT authentication.

| Method | Endpoint | Description | Success Status |
|---|---|---|---|
| POST | `/api/vessels` | Create a vessel | 201 Created |
| GET | `/api/vessels` | Retrieve all vessels | 200 OK |
| GET | `/api/vessels/{id}` | Retrieve vessel by ID | 200 OK |
| PUT | `/api/vessels/{id}` | Update vessel | 200 OK |
| DELETE | `/api/vessels/{id}` | Delete vessel | 200 OK |
| GET | `/api/vessels/search?name={name}` | Search vessels by name | 200 OK |
| GET | `/api/vessels/page` | Retrieve paginated vessels | 200 OK |
| GET | `/api/vessels/status/{status}` | Retrieve vessels by status | 200 OK |
| PATCH | `/api/vessels/{id}/status?status={status}` | Update vessel status | 200 OK |

### Vessel Pagination and Sorting

**Endpoint:** `GET /api/vessels/page`

Query parameters:

| Parameter | Default Value | Description |
|---|---|---|
| page | 0 | Page number |
| size | 10 | Number of records per page |
| sortBy | id | Field used for sorting |
| direction | asc | Sorting direction |

---

## 5. Standard API Response Structure

The application uses a generic `ApiResponse<T>` wrapper.

The response contains:

- `code`: HTTP status code
- `message`: Response message
- `payload`: Response data

Example structure:

```json
{
  "code": 200,
  "message": "Ports Retrieved Successfully",
  "payload": []
}
```

The actual payload depends on the API being called.

## 6. API Documentation and Testing

Swagger UI is integrated using OpenAPI.

Start the Spring Boot application and visit:

`http://localhost:8081/swagger-ui/index.html`

Use Swagger UI to explore endpoints, authorize using a JWT token, and test API requests.

## 7. Error Handling

The application includes centralized exception handling and request validation.

Common HTTP error statuses include:

- 400 – Bad Request / Validation Error
- 401 – Unauthorized
- 403 – Forbidden
- 404 – Resource Not Found
- 409 – Conflict

---

**Project Status:** Work in Progress

Additional APIs, testing, and documentation will be updated as development continues.
