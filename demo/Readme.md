# 🚀 Java Backend Assignment -- Product API (Spring Boot + JWT)

## 📌 Project Overview

This project is a secure RESTful Product Management API built using
**Spring Boot**, **Spring Security (JWT)**, and **PostgreSQL**. It
supports full CRUD operations on Products and authentication using JWT
with HTTP-only cookies.

Base API Version:

    /api/v1/

------------------------------------------------------------------------

## 🧰 Tech Stack

-   Java 21
-   Spring Boot 3.x
-   Spring Security (JWT + Cookie Based Auth)
-   Spring Data JPA (Hibernate)
-   PostgreSQL
-   Lombok
-   Jakarta Validation
-   Maven

------------------------------------------------------------------------

## 🔐 Security Features

-   JWT Authentication (Access + Refresh Token)
-   HTTP-only Cookie based authentication
-   Stateless Session Management
-   Custom AuthenticationEntryPoint & AccessDeniedHandler
-   BCrypt Password Encryption
-   Protected APIs (Login Required)

------------------------------------------------------------------------

## 📂 Project Structure

    src/main/java/com/example/demo
    ├── config/security       # JWT, Security Config, Filters
    ├── controller            # REST Controllers (Auth, Product)
    ├── dto                   # Request & Response DTOs
    ├── entity                # JPA Entities (User, Product, Item)
    ├── repository            # JPA Repositories
    ├── service               # Business Logic Layer
    ├── exception             # Global Exception Handling
    └── util                  # Utility Classes

------------------------------------------------------------------------

## 🗄️ Database Schema

### Product Table

``` sql
CREATE TABLE product (
 id BIGSERIAL PRIMARY KEY,
 product_name VARCHAR(255) NOT NULL,
 created_by VARCHAR(100) NOT NULL,
 created_on TIMESTAMP NOT NULL,
 modified_by VARCHAR(100),
 modified_on TIMESTAMP
);
```

### Item Table

``` sql
CREATE TABLE item (
 id BIGSERIAL PRIMARY KEY,
 product_id BIGINT NOT NULL,
 quantity INT NOT NULL,
 FOREIGN KEY (product_id) REFERENCES product(id)
);
```

------------------------------------------------------------------------

## 🔑 Authentication APIs

### 1️⃣ Register

**POST** `/api/v1/auth/register`

#### Request Body

``` json
{
  "firstName": "Vedansh",
  "lastName": "Tiwari",
  "email": "test@gmail.com",
  "password": "123456"
}
```

#### Response

``` json
{
  "status": "success",
  "message": "User registered successfully"
}
```

------------------------------------------------------------------------

### 2️⃣ Login

**POST** `/api/v1/auth/login`

#### Request Body

``` json
{
  "email": "test@gmail.com",
  "password": "123456"
}
```

#### Response

``` json
{
  "status": "success",
  "message": "Login successful"
}
```

📌 After login, cookies automatically set: - access_token -
refresh_token

------------------------------------------------------------------------

## 📦 Product APIs (Protected -- Login Required)

### 🔍 Get All Products

**GET** `/api/v1/products`

### 🔍 Get Product By ID

**GET** `/api/v1/products/{id}`

Example:

    GET /api/v1/products/1

### ➕ Create Product

**POST** `/api/v1/products`

#### Request Body

``` json
{
  "productName": "Laptop"
}
```

### ✏️ Update Product

**PUT** `/api/v1/products/{id}`

#### Example

    PUT /api/v1/products/1

#### Request Body

``` json
{
  "productName": "Updated Laptop"
}
```

### ❌ Delete Product

**DELETE** `/api/v1/products/{id}`

### 📦 Get Items By Product

**GET** `/api/v1/products/{id}/items`

Example:

    GET /api/v1/products/1/items

------------------------------------------------------------------------

## 🔒 Authorization Rules

  Endpoint                Access
  ----------------------- --------------------
  /api/v1/auth/\*\*       Public
  /api/v1/products/\*\*   Authenticated Only

------------------------------------------------------------------------



### 2️⃣ Configure application.properties

``` properties
spring.datasource.url=jdbc:postgresql://localhost:5432/product_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

security.jwt.secret=ZmFrZXNlY3JldGtleWZha2VzZWNyZXRrZXlmYWtlc2VjcmV0a2V5
```

### 3️⃣ Run Application

``` bash
mvn clean install
mvn spring-boot:run
```

Server URL:

    http://localhost:8080

------------------------------------------------------------------------

## 🧪 API Testing Flow (Postman)

1.  Register → /api/v1/auth/register\
2.  Login → /api/v1/auth/login\
3.  Create Product → /api/v1/products\
4.  Get Products → /api/v1/products

⚠️ Note: Product APIs require login (JWT cookie must be present)

------------------------------------------------------------------------

## 🧠 Architecture Highlights

-   Clean Layered Architecture (Controller → Service → Repository)
-   DTO Pattern Implementation
-   Global Exception Handling
-   Stateless JWT Security
-   Cookie-based Authentication
-   JPA Entity Relationships (Product ↔ Items)

------------------------------------------------------------------------

## 👨‍💻 Author

Vedansh Tiwari
Java Backend Developer Assignment Submission
