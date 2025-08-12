# DVD Rental Management System - API Documentation

## **Overview**
This document provides comprehensive API documentation for the DVD Rental Management System. The system provides RESTful APIs for managing films, actors, customers, rentals, payments, stores, staff, and other business entities.

**Base URL**: `http://localhost:8080/api`  
**Content-Type**: `application/json`  
**CORS**: Enabled for all origins (`*`)

---

## **1. FILMS API** 
**Base Path**: `/api/films`

### **1.1 Get All Films**
- **Endpoint**: `GET /api/films`
- **Description**: Retrieves all films in the system
- **Response**: `200 OK` - List of FilmDTO objects
- **Example Response**:
```json
[
  {
    "filmId": 1,
    "title": "The Matrix",
    "description": "A computer hacker learns from mysterious rebels about the true nature of his reality.",
    "releaseYear": 1999,
    "languageId": 1,
    "rentalDuration": 3,
    "rentalRate": 4.99,
    "length": 136,
    "replacementCost": 19.99,
    "rating": "R",
    "specialFeatures": ["Trailers", "Commentaries"],
    "lastUpdate": "2024-01-15T10:30:00"
  }
]
```

### **1.2 Get Film by ID**
- **Endpoint**: `GET /api/films/{id}`
- **Description**: Retrieves a specific film by its ID
- **Parameters**: `id` (Integer) - Film ID
- **Response**: `200 OK` - FilmDTO object or `404 Not Found`
- **Example Request**: `GET /api/films/1`

### **1.3 Create New Film**
- **Endpoint**: `POST /api/films`
- **Description**: Creates a new film in the system
- **Request Body**: FilmDTO object
- **Response**: `201 Created` - Created FilmDTO object or `400 Bad Request`
- **Example Request Body**:
```json
{
  "title": "New Movie",
  "description": "A new movie description",
  "releaseYear": 2024,
  "languageId": 1,
  "rentalDuration": 3,
  "rentalRate": 4.99,
  "length": 120,
  "replacementCost": 19.99,
  "rating": "PG-13",
  "specialFeatures": ["Trailers"]
}
```

### **1.4 Update Film**
- **Endpoint**: `PUT /api/films/{id}`
- **Description**: Updates an existing film
- **Parameters**: `id` (Integer) - Film ID
- **Request Body**: FilmDTO object
- **Response**: `200 OK` - Updated FilmDTO object, `404 Not Found`, or `400 Bad Request`

### **1.5 Delete Film**
- **Endpoint**: `DELETE /api/films/{id}`
- **Description**: Deletes a film from the system
- **Parameters**: `id` (Integer) - Film ID
- **Response**: `204 No Content` or `404 Not Found`

### **1.6 Search Films by Title**
- **Endpoint**: `GET /api/films/search?title={title}`
- **Description**: Searches films by title (case-insensitive)
- **Query Parameters**: `title` (String) - Search term
- **Response**: `200 OK` - List of matching FilmDTO objects
- **Example Request**: `GET /api/films/search?title=matrix`

### **1.7 Get Films by Year**
- **Endpoint**: `GET /api/films/year/{year}`
- **Description**: Retrieves all films released in a specific year
- **Parameters**: `year` (Integer) - Release year
- **Response**: `200 OK` - List of FilmDTO objects
- **Example Request**: `GET /api/films/year/1999`

### **1.8 Get Films by Language**
- **Endpoint**: `GET /api/films/language/{languageId}`
- **Description**: Retrieves all films in a specific language
- **Parameters**: `languageId` (Short) - Language ID
- **Response**: `200 OK` - List of FilmDTO objects
- **Example Request**: `GET /api/films/language/1`

### **1.9 Get Films by Rating**
- **Endpoint**: `GET /api/films/rating/{rating}`
- **Description**: Retrieves all films with a specific MPAA rating
- **Parameters**: `rating` (String) - MPAA rating (G, PG, PG-13, R, NC-17)
- **Response**: `200 OK` - List of FilmDTO objects
- **Example Request**: `GET /api/films/rating/R`

### **1.10 Get Films by Rental Rate Range**
- **Endpoint**: `GET /api/films/rental-rate?minRate={min}&maxRate={max}`
- **Description**: Retrieves films within a rental rate range
- **Query Parameters**: 
  - `minRate` (BigDecimal) - Minimum rental rate
  - `maxRate` (BigDecimal) - Maximum rental rate
- **Response**: `200 OK` - List of FilmDTO objects
- **Example Request**: `GET /api/films/rental-rate?minRate=2.99&maxRate=5.99`

### **1.11 Search Films by Keyword**
- **Endpoint**: `GET /api/films/keyword?keyword={keyword}`
- **Description**: Searches films by keyword in title or description
- **Query Parameters**: `keyword` (String) - Search keyword
- **Response**: `200 OK` - List of matching FilmDTO objects
- **Example Request**: `GET /api/films/keyword?keyword=action`

---

## **2. ACTORS API**
**Base Path**: `/api/actors`

### **2.1 Get All Actors**
- **Endpoint**: `GET /api/actors`
- **Description**: Retrieves all actors in the system
- **Response**: `200 OK` - List of ActorDTO objects

### **2.2 Get Actor by ID**
- **Endpoint**: `GET /api/actors/{id}`
- **Description**: Retrieves a specific actor by ID
- **Parameters**: `id` (Integer) - Actor ID
- **Response**: `200 OK` - ActorDTO object or `404 Not Found`

### **2.3 Create New Actor**
- **Endpoint**: `POST /api/actors`
- **Description**: Creates a new actor
- **Request Body**: ActorDTO object
- **Response**: `201 Created` - Created ActorDTO object or `400 Bad Request`

### **2.4 Update Actor**
- **Endpoint**: `PUT /api/actors/{id}`
- **Description**: Updates an existing actor
- **Parameters**: `id` (Integer) - Actor ID
- **Request Body**: ActorDTO object
- **Response**: `200 OK` - Updated ActorDTO object, `404 Not Found`, or `400 Bad Request`

### **2.5 Delete Actor**
- **Endpoint**: `DELETE /api/actors/{id}`
- **Description**: Deletes an actor
- **Parameters**: `id` (Integer) - Actor ID
- **Response**: `204 No Content` or `404 Not Found`

### **2.6 Search Actors by Name**
- **Endpoint**: `GET /api/actors/search?name={name}`
- **Description**: Searches actors by name
- **Query Parameters**: `name` (String) - Actor name
- **Response**: `200 OK` - List of matching ActorDTO objects

---

## **3. CUSTOMERS API**
**Base Path**: `/api/customers`

### **3.1 Get All Customers**
- **Endpoint**: `GET /api/customers`
- **Description**: Retrieves all customers
- **Response**: `200 OK` - List of CustomerDTO objects

### **3.2 Get Customer by ID**
- **Endpoint**: `GET /api/customers/{id}`
- **Description**: Retrieves a specific customer by ID
- **Parameters**: `id` (Integer) - Customer ID
- **Response**: `200 OK` - CustomerDTO object or `404 Not Found`

### **3.3 Create New Customer**
- **Endpoint**: `POST /api/customers`
- **Description**: Creates a new customer
- **Request Body**: CustomerDTO object
- **Response**: `201 Created` - Created CustomerDTO object or `400 Bad Request`

### **3.4 Update Customer**
- **Endpoint**: `PUT /api/customers/{id}`
- **Description**: Updates an existing customer
- **Parameters**: `id` (Integer) - Customer ID
- **Request Body**: CustomerDTO object
- **Response**: `200 OK` - Updated CustomerDTO object, `404 Not Found`, or `400 Bad Request`

### **3.5 Delete Customer**
- **Endpoint**: `DELETE /api/customers/{id}`
- **Description**: Deletes a customer
- **Parameters**: `id` (Integer) - Customer ID
- **Response**: `204 No Content` or `404 Not Found`

### **3.6 Search Customers by Name**
- **Endpoint**: `GET /api/customers/search?name={name}`
- **Description**: Searches customers by name
- **Query Parameters**: `name` (String) - Customer name
- **Response**: `200 OK` - List of matching CustomerDTO objects

### **3.7 Get Customers by Store**
- **Endpoint**: `GET /api/customers/store/{storeId}`
- **Description**: Retrieves all customers for a specific store
- **Parameters**: `storeId` (Short) - Store ID
- **Response**: `200 OK` - List of CustomerDTO objects

### **3.8 Get Customer by Email**
- **Endpoint**: `GET /api/customers/email?email={email}`
- **Description**: Retrieves a customer by email address
- **Query Parameters**: `email` (String) - Customer email
- **Response**: `200 OK` - CustomerDTO object or `404 Not Found`

---

## **4. RENTALS API**
**Base Path**: `/api/rentals`

### **4.1 Get All Rentals**
- **Endpoint**: `GET /api/rentals`
- **Description**: Retrieves all rental transactions
- **Response**: `200 OK` - List of RentalDTO objects

### **4.2 Get Rental by ID**
- **Endpoint**: `GET /api/rentals/{id}`
- **Description**: Retrieves a specific rental by ID
- **Parameters**: `id` (Integer) - Rental ID
- **Response**: `200 OK` - RentalDTO object or `404 Not Found`

### **4.3 Create New Rental**
- **Endpoint**: `POST /api/rentals`
- **Description**: Creates a new rental transaction
- **Request Body**: RentalDTO object
- **Response**: `201 Created` - Created RentalDTO object or `400 Bad Request`

### **4.4 Update Rental**
- **Endpoint**: `PUT /api/rentals/{id}`
- **Description**: Updates an existing rental
- **Parameters**: `id` (Integer) - Rental ID
- **Request Body**: RentalDTO object
- **Response**: `200 OK` - Updated RentalDTO object, `404 Not Found`, or `400 Bad Request`

### **4.5 Delete Rental**
- **Endpoint**: `DELETE /api/rentals/{id}`
- **Description**: Deletes a rental transaction
- **Parameters**: `id` (Integer) - Rental ID
- **Response**: `204 No Content` or `404 Not Found`

### **4.6 Get Rentals by Customer**
- **Endpoint**: `GET /api/rentals/customer/{customerId}`
- **Description**: Retrieves all rentals for a specific customer
- **Parameters**: `customerId` (Short) - Customer ID
- **Response**: `200 OK` - List of RentalDTO objects

### **4.7 Get Active Rentals by Customer**
- **Endpoint**: `GET /api/rentals/active/{customerId}`
- **Description**: Retrieves active (unreturned) rentals for a customer
- **Parameters**: `customerId` (Short) - Customer ID
- **Response**: `200 OK` - List of RentalDTO objects

---

## **5. PAYMENTS API**
**Base Path**: `/api/payments`

### **5.1 Get All Payments**
- **Endpoint**: `GET /api/payments`
- **Description**: Retrieves all payment transactions
- **Response**: `200 OK` - List of PaymentDTO objects

### **5.2 Get Payment by ID**
- **Endpoint**: `GET /api/payments/{id}`
- **Description**: Retrieves a specific payment by ID
- **Parameters**: `id` (Integer) - Payment ID
- **Response**: `200 OK` - PaymentDTO object or `404 Not Found`

### **5.3 Create New Payment**
- **Endpoint**: `POST /api/payments`
- **Description**: Creates a new payment transaction
- **Request Body**: PaymentDTO object
- **Response**: `201 Created` - Created PaymentDTO object or `400 Bad Request`

### **5.4 Get Payments by Customer**
- **Endpoint**: `GET /api/payments/customer/{customerId}`
- **Description**: Retrieves all payments for a specific customer
- **Parameters**: `customerId` (Short) - Customer ID
- **Response**: `200 OK` - List of PaymentDTO objects

### **5.5 Get Total Payments by Customer**
- **Endpoint**: `GET /api/payments/customer/{customerId}/total`
- **Description**: Retrieves the total amount paid by a customer
- **Parameters**: `customerId` (Short) - Customer ID
- **Response**: `200 OK` - Total amount (BigDecimal)

---

## **6. STORES API**
**Base Path**: `/api/stores`

### **6.1 Get All Stores**
- **Endpoint**: `GET /api/stores`
- **Description**: Retrieves all store locations
- **Response**: `200 OK` - List of StoreDTO objects

### **6.2 Get Store by ID**
- **Endpoint**: `GET /api/stores/{id}`
- **Description**: Retrieves a specific store by ID
- **Parameters**: `id` (Integer) - Store ID
- **Response**: `200 OK` - StoreDTO object or `404 Not Found`

### **6.3 Create New Store**
- **Endpoint**: `POST /api/stores`
- **Description**: Creates a new store location
- **Request Body**: StoreDTO object
- **Response**: `201 Created` - Created StoreDTO object or `400 Bad Request`

---

## **7. STAFF API**
**Base Path**: `/api/staff`

### **7.1 Get All Staff**
- **Endpoint**: `GET /api/staff`
- **Description**: Retrieves all staff members
- **Response**: `200 OK` - List of StaffDTO objects

### **7.2 Get Staff by ID**
- **Endpoint**: `GET /api/staff/{id}`
- **Description**: Retrieves a specific staff member by ID
- **Parameters**: `id` (Integer) - Staff ID
- **Response**: `200 OK` - StaffDTO object or `404 Not Found`

### **7.3 Create New Staff**
- **Endpoint**: `POST /api/staff`
- **Description**: Creates a new staff member
- **Request Body**: StaffDTO object
- **Response**: `201 Created` - Created StaffDTO object or `400 Bad Request`

### **7.4 Update Staff**
- **Endpoint**: `PUT /api/staff/{id}`
- **Description**: Updates an existing staff member
- **Parameters**: `id` (Integer) - Staff ID
- **Request Body**: StaffDTO object
- **Response**: `200 OK` - Updated StaffDTO object, `404 Not Found`, or `400 Bad Request`

### **7.5 Get Staff by Store**
- **Endpoint**: `GET /api/staff/store/{storeId}`
- **Description**: Retrieves all staff members for a specific store
- **Parameters**: `storeId` (Short) - Store ID
- **Response**: `200 OK` - List of StaffDTO objects

---

## **8. CATEGORIES API**
**Base Path**: `/api/categories`

### **8.1 Get All Categories**
- **Endpoint**: `GET /api/categories`
- **Description**: Retrieves all film categories
- **Response**: `200 OK` - List of CategoryDTO objects

### **8.2 Get Category by ID**
- **Endpoint**: `GET /api/categories/{id}`
- **Description**: Retrieves a specific category by ID
- **Parameters**: `id` (Integer) - Category ID
- **Response**: `200 OK` - CategoryDTO object or `404 Not Found`

### **8.3 Create New Category**
- **Endpoint**: `POST /api/categories`
- **Description**: Creates a new film category
- **Request Body**: CategoryDTO object
- **Response**: `201 Created` - Created CategoryDTO object or `400 Bad Request`

### **8.4 Update Category**
- **Endpoint**: `PUT /api/categories/{id}`
- **Description**: Updates an existing category
- **Parameters**: `id` (Integer) - Category ID
- **Request Body**: CategoryDTO object
- **Response**: `200 OK` - Updated CategoryDTO object, `404 Not Found`, or `400 Bad Request`

### **8.5 Delete Category**
- **Endpoint**: `DELETE /api/categories/{id}`
- **Description**: Deletes a category
- **Parameters**: `id` (Integer) - Category ID
- **Response**: `204 No Content` or `404 Not Found`

---

## **9. COUNTRIES API**
**Base Path**: `/api/countries`

### **9.1 Get All Countries**
- **Endpoint**: `GET /api/countries`
- **Description**: Retrieves all countries
- **Response**: `200 OK` - List of CountryDTO objects

### **9.2 Get Country by ID**
- **Endpoint**: `GET /api/countries/{id}`
- **Description**: Retrieves a specific country by ID
- **Parameters**: `id` (Integer) - Country ID
- **Response**: `200 OK` - CountryDTO object or `404 Not Found`

### **9.3 Create New Country**
- **Endpoint**: `POST /api/countries`
- **Description**: Creates a new country
- **Request Body**: CountryDTO object
- **Response**: `201 Created` - Created CountryDTO object or `400 Bad Request`

### **9.4 Update Country**
- **Endpoint**: `PUT /api/countries/{id}`
- **Description**: Updates an existing country
- **Parameters**: `id` (Integer) - Country ID
- **Request Body**: CountryDTO object
- **Response**: `200 OK` - Updated CountryDTO object, `404 Not Found`, or `400 Bad Request`

### **9.5 Delete Country**
- **Endpoint**: `DELETE /api/countries/{id}`
- **Description**: Deletes a country
- **Parameters**: `id` (Integer) - Country ID
- **Response**: `204 No Content` or `404 Not Found`

### **9.6 Search Countries by Name**
- **Endpoint**: `GET /api/countries/search?name={name}`
- **Description**: Searches countries by name
- **Query Parameters**: `name` (String) - Country name
- **Response**: `200 OK` - List of matching CountryDTO objects

---

## **10. COMMON RESPONSE FORMATS**

### **Success Responses**
- **200 OK**: Successful GET, PUT operations
- **201 Created**: Successful POST operations
- **204 No Content**: Successful DELETE operations

### **Error Responses**
- **400 Bad Request**: Invalid request data or validation errors
- **404 Not Found**: Resource not found
- **500 Internal Server Error**: Server-side errors

### **Response Headers**
```
Content-Type: application/json
Access-Control-Allow-Origin: *
```

---

## **11. DATA TRANSFER OBJECTS (DTOs)**

### **FilmDTO**
```json
{
  "filmId": "Integer",
  "title": "String",
  "description": "String",
  "releaseYear": "Integer",
  "languageId": "Short",
  "rentalDuration": "Short",
  "rentalRate": "BigDecimal",
  "length": "Short",
  "replacementCost": "BigDecimal",
  "rating": "String",
  "specialFeatures": "String[]",
  "lastUpdate": "LocalDateTime"
}
```

### **ActorDTO**
```json
{
  "actorId": "Integer",
  "firstName": "String",
  "lastName": "String",
  "lastUpdate": "LocalDateTime"
}
```

### **CustomerDTO**
```json
{
  "customerId": "Integer",
  "storeId": "Short",
  "firstName": "String",
  "lastName": "String",
  "email": "String",
  "addressId": "Integer",
  "activebool": "Boolean",
  "createDate": "LocalDate",
  "lastUpdate": "LocalDateTime",
  "active": "Integer"
}
```

### **RentalDTO**
```json
{
  "rentalId": "Integer",
  "rentalDate": "LocalDateTime",
  "inventoryId": "Integer",
  "customerId": "Short",
  "returnDate": "LocalDateTime",
  "staffId": "Short",
  "lastUpdate": "LocalDateTime"
}
```

### **PaymentDTO**
```json
{
  "paymentId": "Integer",
  "customerId": "Short",
  "staffId": "Short",
  "rentalId": "Integer",
  "amount": "BigDecimal",
  "paymentDate": "LocalDateTime"
}
```

---

## **12. USAGE EXAMPLES**

### **Complete Film Management Workflow**
```bash
# 1. Create a new film
POST /api/films
{
  "title": "The Shawshank Redemption",
  "description": "Two imprisoned men bond over a number of years...",
  "releaseYear": 1994,
  "languageId": 1,
  "rentalRate": 4.99
}

# 2. Search for the film
GET /api/films/search?title=shawshank

# 3. Update film details
PUT /api/films/{filmId}
{
  "rentalRate": 5.99
}

# 4. Delete the film
DELETE /api/films/{filmId}
```

### **Customer Rental Workflow**
```bash
# 1. Create a customer
POST /api/customers
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@email.com",
  "storeId": 1
}

# 2. Create a rental
POST /api/rentals
{
  "customerId": 1,
  "filmId": 1,
  "rentalDate": "2024-01-15T10:00:00"
}

# 3. Create payment
POST /api/payments
{
  "customerId": 1,
  "rentalId": 1,
  "amount": 4.99
}

# 4. Return the rental
PUT /api/rentals/{rentalId}
{
  "returnDate": "2024-01-18T10:00:00"
}
```

---

## **13. ERROR HANDLING**

### **Common Error Scenarios**
1. **Invalid ID**: Returns `404 Not Found`
2. **Missing Required Fields**: Returns `400 Bad Request`
3. **Data Validation Errors**: Returns `400 Bad Request`
4. **Database Constraints**: Returns `400 Bad Request`
5. **Server Errors**: Returns `500 Internal Server Error`

### **Error Response Format**
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/films"
}
```

---

## **14. RATE LIMITING & SECURITY**

### **Current Implementation**
- **CORS**: Enabled for all origins
- **Authentication**: Not implemented (development mode)
- **Rate Limiting**: Not implemented
- **Input Validation**: Basic validation through DTOs

### **Production Recommendations**
- Implement JWT authentication
- Add rate limiting (e.g., 100 requests per minute per IP)
- Enable HTTPS
- Implement input sanitization
- Add API key management for external clients

---

## **15. TESTING THE API**

### **Using cURL**
```bash
# Get all films
curl -X GET "http://localhost:8080/api/films"

# Create a film
curl -X POST "http://localhost:8080/api/films" \
  -H "Content-Type: application/json" \
  -d '{"title":"Test Film","languageId":1}'

# Search films
curl -X GET "http://localhost:8080/api/films/search?title=test"
```

### **Using Postman**
1. Import the collection
2. Set base URL: `http://localhost:8080/api`
3. Test individual endpoints
4. Verify response codes and data

---

## **16. MONITORING & LOGGING**

### **Current Logging**
- SQL queries are logged at DEBUG level
- Controller and service operations are logged
- Error responses are logged

### **Recommended Monitoring**
- API response times
- Error rates by endpoint
- Database connection pool status
- Memory and CPU usage
- Request volume by endpoint

---

This API documentation provides a comprehensive guide to all endpoints in the DVD Rental Management System. Each endpoint includes detailed information about request/response formats, parameters, and usage examples.
