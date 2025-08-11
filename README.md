# DVD Rental Management System

A comprehensive Spring Boot REST API application for managing a DVD rental store system using PostgreSQL database.

## Overview

This application provides a complete CRUD (Create, Read, Update, Delete) API for managing all aspects of a DVD rental business, including films, actors, customers, rentals, payments, and inventory management.

## Technologies Used

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **Jakarta Persistence API (JPA)**

## Database Schema

The application works with the classic **dvdrental** PostgreSQL database which includes 15 main tables:

### Core Tables
1. **country** - Country information
2. **city** - Cities with country relationships
3. **address** - Address details
4. **language** - Film languages
5. **category** - Film categories
6. **actor** - Actor information
7. **film** - Movie details and metadata
8. **store** - Store locations
9. **staff** - Employee information
10. **customer** - Customer data
11. **inventory** - Film inventory tracking
12. **rental** - Rental transactions
13. **payment** - Payment records
14. **film_actor** - Film-Actor relationships (Many-to-Many)
15. **film_category** - Film-Category relationships (Many-to-Many)

## Project Structure

```
src/main/java/com/dvdrental/management/
├── entity/          # JPA entities mapping to database tables
├── dto/             # Data Transfer Objects using Java records
├── repository/      # Spring Data JPA repositories
├── service/         # Business logic layer
├── controller/      # REST API controllers
└── DvdRentalManagementApplication.java
```

## Configuration

### Database Configuration

Update `src/main/resources/application.properties` with your PostgreSQL credentials:

```properties
# PostgreSQL database configuration for dvdrental
spring.datasource.url=jdbc:postgresql://localhost:5432/dvdrental
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA configurations
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

## API Endpoints

### Films API (`/api/films`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/films` | Get all films |
| GET | `/api/films/{id}` | Get film by ID |
| POST | `/api/films` | Create new film |
| PUT | `/api/films/{id}` | Update existing film |
| DELETE | `/api/films/{id}` | Delete film |
| GET | `/api/films/search?title={title}` | Search films by title |
| GET | `/api/films/year/{year}` | Get films by release year |
| GET | `/api/films/language/{languageId}` | Get films by language |
| GET | `/api/films/rating/{rating}` | Get films by rating |
| GET | `/api/films/rental-rate?minRate={min}&maxRate={max}` | Get films by rental rate range |
| GET | `/api/films/keyword?keyword={keyword}` | Search films by keyword |

### Actors API (`/api/actors`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/actors` | Get all actors |
| GET | `/api/actors/{id}` | Get actor by ID |
| POST | `/api/actors` | Create new actor |
| PUT | `/api/actors/{id}` | Update existing actor |
| DELETE | `/api/actors/{id}` | Delete actor |
| GET | `/api/actors/search?name={name}` | Search actors by name |

### Additional APIs

The application provides similar CRUD endpoints for all 15 tables:
- `/api/countries`
- `/api/cities`
- `/api/addresses`
- `/api/languages`
- `/api/categories`
- `/api/stores`
- `/api/staff`
- `/api/customers`
- `/api/inventory`
- `/api/rentals`
- `/api/payments`
- `/api/film-actors`
- `/api/film-categories`

## Sample API Usage

### Get All Films
```bash
curl -X GET http://localhost:8080/api/films
```

### Create New Actor
```bash
curl -X POST http://localhost:8080/api/actors \
-H "Content-Type: application/json" \
-d '{
  "firstName": "John",
  "lastName": "Doe"
}'
```

### Search Films by Title
```bash
curl -X GET "http://localhost:8080/api/films/search?title=Academy"
```

### Get Films by Rating
```bash
curl -X GET http://localhost:8080/api/films/rating/PG-13
```

## Running the Application

### Prerequisites
- Java 17 or later
- Maven 3.6+
- PostgreSQL with dvdrental database

### Steps
1. Clone the repository
2. Update database credentials in `application.properties`
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. The API will be available at `http://localhost:8080`

## Features

- **Complete CRUD Operations** for all 15 database tables
- **Advanced Search Capabilities** with custom query methods
- **RESTful API Design** following best practices
- **Data Transfer Objects (DTOs)** using Java records
- **JPA Entity Relationships** properly mapped
- **Error Handling** with appropriate HTTP status codes
- **Cross-Origin Resource Sharing (CORS)** enabled
- **Database Validation** mode to preserve existing data

## Architecture

The application follows a **three-layer architecture**:

1. **Controller Layer** - Handles HTTP requests and responses
2. **Service Layer** - Contains business logic and data transformation
3. **Repository Layer** - Manages data persistence operations

## Entity Relationships

- **Country** → **City** (One-to-Many)
- **City** → **Address** (One-to-Many)
- **Language** → **Film** (One-to-Many)
- **Film** ↔ **Actor** (Many-to-Many via film_actor)
- **Film** ↔ **Category** (Many-to-Many via film_category)
- **Store** → **Customer** (One-to-Many)
- **Store** → **Staff** (One-to-Many)
- **Store** → **Inventory** (One-to-Many)
- **Customer** → **Rental** (One-to-Many)
- **Staff** → **Rental** (One-to-Many)
- **Rental** → **Payment** (One-to-Many)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.
