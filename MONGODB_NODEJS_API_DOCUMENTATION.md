# MongoDB + Node.js DVD Rental System - API Documentation

## **Overview**
This document provides comprehensive API documentation for the DVD Rental Management System implemented with **MongoDB** (document database) and **Node.js** (Express.js framework). The system provides RESTful APIs for managing films, actors, customers, rentals, payments, stores, staff, and other business entities using a NoSQL approach.

**Base URL**: `http://localhost:3000/api`  
**Content-Type**: `application/json`  
**Database**: MongoDB (mongodb://localhost:27017/dvdrental)  
**Framework**: Express.js with MongoDB driver

---

## **1. FILMS API** 
**Base Path**: `/api/films`

### **1.1 Get All Films**
- **Endpoint**: `GET /api/films`
- **Description**: Retrieves all films with embedded actor and category information
- **Response**: `200 OK` - Array of film documents
- **MongoDB Query**: `db.films.find({})`
- **Example Response**:
```json
[
  {
    "_id": "ObjectId('507f1f77bcf86cd799439011')",
    "title": "The Matrix",
    "description": "A computer hacker learns from mysterious rebels about the true nature of his reality.",
    "releaseYear": 1999,
    "language": "English",
    "rentalDuration": 3,
    "rentalRate": 4.99,
    "length": 136,
    "replacementCost": 19.99,
    "rating": "R",
    "specialFeatures": ["Trailers", "Commentaries", "Behind the Scenes"],
    "actors": [
      {
        "actorId": "ObjectId('507f1f77bcf86cd799439012')",
        "name": "Keanu Reeves",
        "role": "Neo"
      },
      {
        "actorId": "ObjectId('507f1f77bcf86cd799439013')",
        "name": "Laurence Fishburne",
        "role": "Morpheus"
      }
    ],
    "categories": ["Action", "Sci-Fi", "Thriller"],
    "inventory": [
      {
        "inventoryId": "ObjectId('507f1f77bcf86cd799439014')",
        "storeId": 1,
        "status": "available"
      }
    ],
    "createdAt": "2024-01-15T10:30:00.000Z",
    "updatedAt": "2024-01-15T10:30:00.000Z"
  }
]
```

### **1.2 Get Film by ID**
- **Endpoint**: `GET /api/films/:id`
- **Description**: Retrieves a specific film by MongoDB ObjectId
- **Parameters**: `id` (ObjectId) - Film ObjectId
- **MongoDB Query**: `db.films.findOne({_id: ObjectId(id)})`
- **Response**: `200 OK` - Film document or `404 Not Found`

### **1.3 Create New Film**
- **Endpoint**: `POST /api/films`
- **Description**: Creates a new film with embedded actor and category data
- **Request Body**: Film document structure
- **MongoDB Operation**: `db.films.insertOne(filmData)`
- **Response**: `201 Created` - Created film document with ObjectId
- **Example Request Body**:
```json
{
  "title": "New Movie",
  "description": "A new movie description",
  "releaseYear": 2024,
  "language": "English",
  "rentalDuration": 3,
  "rentalRate": 4.99,
  "length": 120,
  "replacementCost": 19.99,
  "rating": "PG-13",
  "specialFeatures": ["Trailers"],
  "actors": [
    {
      "name": "Actor Name",
      "role": "Character Role"
    }
  ],
  "categories": ["Action", "Adventure"]
}
```

### **1.4 Update Film**
- **Endpoint**: `PUT /api/films/:id`
- **Description**: Updates an existing film document
- **Parameters**: `id` (ObjectId) - Film ObjectId
- **MongoDB Operation**: `db.films.updateOne({_id: ObjectId(id)}, {$set: updateData})`
- **Response**: `200 OK` - Updated film document

### **1.5 Delete Film**
- **Endpoint**: `DELETE /api/films/:id`
- **Description**: Deletes a film and related inventory
- **Parameters**: `id` (ObjectId) - Film ObjectId
- **MongoDB Operations**: 
  - `db.films.deleteOne({_id: ObjectId(id)})`
  - `db.inventory.deleteMany({filmId: ObjectId(id)})`
- **Response**: `204 No Content`

### **1.6 Search Films by Title**
- **Endpoint**: `GET /api/films/search?title={title}`
- **Description**: Text search using MongoDB text index
- **Query Parameters**: `title` (String) - Search term
- **MongoDB Query**: `db.films.find({$text: {$search: title}})`
- **Response**: `200 OK` - Array of matching film documents

### **1.7 Get Films by Year**
- **Endpoint**: `GET /api/films/year/:year`
- **Description**: Retrieves films by release year
- **Parameters**: `year` (Integer) - Release year
- **MongoDB Query**: `db.films.find({releaseYear: year})`
- **Response**: `200 OK` - Array of film documents

### **1.8 Get Films by Rating**
- **Endpoint**: `GET /api/films/rating/:rating`
- **Description**: Retrieves films by MPAA rating
- **Parameters**: `rating` (String) - MPAA rating
- **MongoDB Query**: `db.films.find({rating: rating})`
- **Response**: `200 OK` - Array of film documents

### **1.9 Get Films by Rental Rate Range**
- **Endpoint**: `GET /api/films/rental-rate?minRate={min}&maxRate={max}`
- **Description**: Retrieves films within rental rate range
- **Query Parameters**: 
  - `minRate` (Number) - Minimum rental rate
  - `maxRate` (Number) - Maximum rental rate
- **MongoDB Query**: `db.films.find({rentalRate: {$gte: minRate, $lte: maxRate}})`
- **Response**: `200 OK` - Array of film documents

### **1.10 Search Films by Keyword**
- **Endpoint**: `GET /api/films/keyword?keyword={keyword}`
- **Description**: Full-text search across title and description
- **Query Parameters**: `keyword` (String) - Search keyword
- **MongoDB Query**: `db.films.find({$text: {$search: keyword}})`
- **Response**: `200 OK` - Array of matching film documents

### **1.11 Get Films by Category**
- **Endpoint**: `GET /api/films/category/:category`
- **Description**: Retrieves films by category
- **Parameters**: `category` (String) - Category name
- **MongoDB Query**: `db.films.find({categories: category})`
- **Response**: `200 OK` - Array of film documents

---

## **2. ACTORS API**
**Base Path**: `/api/actors`

### **2.1 Get All Actors**
- **Endpoint**: `GET /api/actors`
- **Description**: Retrieves all actors with film information
- **MongoDB Query**: `db.actors.find({})`
- **Response**: `200 OK` - Array of actor documents

### **2.2 Get Actor by ID**
- **Endpoint**: `GET /api/actors/:id`
- **Description**: Retrieves actor by ObjectId
- **Parameters**: `id` (ObjectId) - Actor ObjectId
- **MongoDB Query**: `db.actors.findOne({_id: ObjectId(id)})`
- **Response**: `200 OK` - Actor document or `404 Not Found`

### **2.3 Create New Actor**
- **Endpoint**: `POST /api/actors`
- **Description**: Creates new actor
- **Request Body**: Actor document
- **MongoDB Operation**: `db.actors.insertOne(actorData)`
- **Response**: `201 Created` - Created actor document

### **2.4 Update Actor**
- **Endpoint**: `PUT /api/actors/:id`
- **Description**: Updates existing actor
- **Parameters**: `id` (ObjectId) - Actor ObjectId
- **MongoDB Operation**: `db.actors.updateOne({_id: ObjectId(id)}, {$set: updateData})`
- **Response**: `200 OK` - Updated actor document

### **2.5 Delete Actor**
- **Endpoint**: `DELETE /api/actors/:id`
- **Description**: Deletes actor and removes from films
- **Parameters**: `id` (ObjectId) - Actor ObjectId
- **MongoDB Operations**:
  - `db.actors.deleteOne({_id: ObjectId(id)})`
  - `db.films.updateMany({}, {$pull: {actors: {actorId: ObjectId(id)}}})`
- **Response**: `204 No Content`

### **2.6 Search Actors by Name**
- **Endpoint**: `GET /api/actors/search?name={name}`
- **Description**: Searches actors by name
- **Query Parameters**: `name` (String) - Actor name
- **MongoDB Query**: `db.actors.find({name: {$regex: name, $options: 'i'}})`
- **Response**: `200 OK` - Array of matching actor documents

### **2.7 Get Actor's Films**
- **Endpoint**: `GET /api/actors/:id/films`
- **Description**: Retrieves all films featuring a specific actor
- **Parameters**: `id` (ObjectId) - Actor ObjectId
- **MongoDB Query**: `db.films.find({"actors.actorId": ObjectId(id)})`
- **Response**: `200 OK` - Array of film documents

---

## **3. CUSTOMERS API**
**Base Path**: `/api/customers`

### **3.1 Get All Customers**
- **Endpoint**: `GET /api/customers`
- **Description**: Retrieves all customers with address information
- **MongoDB Query**: `db.customers.find({})`
- **Response**: `200 OK` - Array of customer documents

### **3.2 Get Customer by ID**
- **Endpoint**: `GET /api/customers/:id`
- **Description**: Retrieves customer by ObjectId
- **Parameters**: `id` (ObjectId) - Customer ObjectId
- **MongoDB Query**: `db.customers.findOne({_id: ObjectId(id)})`
- **Response**: `200 OK` - Customer document or `404 Not Found`

### **3.3 Create New Customer**
- **Endpoint**: `POST /api/customers`
- **Description**: Creates new customer with embedded address
- **Request Body**: Customer document
- **MongoDB Operation**: `db.customers.insertOne(customerData)`
- **Response**: `201 Created` - Created customer document
- **Example Request Body**:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@email.com",
  "storeId": 1,
  "address": {
    "street": "123 Main St",
    "city": "New York",
    "state": "NY",
    "postalCode": "10001",
    "country": "USA"
  },
  "active": true,
  "rentals": []
}
```

### **3.4 Update Customer**
- **Endpoint**: `PUT /api/customers/:id`
- **Description**: Updates existing customer
- **Parameters**: `id` (ObjectId) - Customer ObjectId
- **MongoDB Operation**: `db.customers.updateOne({_id: ObjectId(id)}, {$set: updateData})`
- **Response**: `200 OK` - Updated customer document

### **3.5 Delete Customer**
- **Endpoint**: `DELETE /api/customers/:id`
- **Description**: Deletes customer and related data
- **Parameters**: `id` (ObjectId) - Customer ObjectId
- **MongoDB Operations**:
  - `db.customers.deleteOne({_id: ObjectId(id)})`
  - `db.rentals.deleteMany({customerId: ObjectId(id)})`
  - `db.payments.deleteMany({customerId: ObjectId(id)})`
- **Response**: `204 No Content`

### **3.6 Search Customers by Name**
- **Endpoint**: `GET /api/customers/search?name={name}`
- **Description**: Searches customers by name
- **Query Parameters**: `name` (String) - Customer name
- **MongoDB Query**: `db.customers.find({$or: [{firstName: {$regex: name, $options: 'i'}}, {lastName: {$regex: name, $options: 'i'}}]})`
- **Response**: `200 OK` - Array of matching customer documents

### **3.7 Get Customers by Store**
- **Endpoint**: `GET /api/customers/store/:storeId`
- **Description**: Retrieves customers for specific store
- **Parameters**: `storeId` (Integer) - Store ID
- **MongoDB Query**: `db.customers.find({storeId: storeId})`
- **Response**: `200 OK` - Array of customer documents

### **3.8 Get Customer by Email**
- **Endpoint**: `GET /api/customers/email?email={email}`
- **Description**: Retrieves customer by email
- **Query Parameters**: `email` (String) - Customer email
- **MongoDB Query**: `db.customers.findOne({email: email})`
- **Response**: `200 OK` - Customer document or `404 Not Found`

---

## **4. RENTALS API**
**Base Path**: `/api/rentals`

### **4.1 Get All Rentals**
- **Endpoint**: `GET /api/rentals`
- **Description**: Retrieves all rental transactions with embedded data
- **MongoDB Query**: `db.rentals.find({})`
- **Response**: `200 OK` - Array of rental documents

### **4.2 Get Rental by ID**
- **Endpoint**: `GET /api/rentals/:id`
- **Description**: Retrieves rental by ObjectId
- **Parameters**: `id` (ObjectId) - Rental ObjectId
- **MongoDB Query**: `db.rentals.findOne({_id: ObjectId(id)})`
- **Response**: `200 OK` - Rental document or `404 Not Found`

### **4.3 Create New Rental**
- **Endpoint**: `POST /api/rentals`
- **Description**: Creates new rental transaction
- **Request Body**: Rental document
- **MongoDB Operations**:
  - `db.rentals.insertOne(rentalData)`
  - `db.inventory.updateOne({_id: ObjectId(inventoryId)}, {$set: {status: "rented"}})`
  - `db.customers.updateOne({_id: ObjectId(customerId)}, {$push: {rentals: ObjectId(rentalId)}})`
- **Response**: `201 Created` - Created rental document
- **Example Request Body**:
```json
{
  "customerId": "ObjectId('507f1f77bcf86cd799439011')",
  "filmId": "ObjectId('507f1f77bcf86cd799439012')",
  "inventoryId": "ObjectId('507f1f77bcf86cd799439013')",
  "staffId": "ObjectId('507f1f77bcf86cd799439014')",
  "rentalDate": "2024-01-15T10:00:00.000Z",
  "rentalDuration": 3,
  "rentalRate": 4.99
}
```

### **4.4 Update Rental**
- **Endpoint**: `PUT /api/rentals/:id`
- **Description**: Updates existing rental (e.g., return date)
- **Parameters**: `id` (ObjectId) - Rental ObjectId
- **MongoDB Operation**: `db.rentals.updateOne({_id: ObjectId(id)}, {$set: updateData})`
- **Response**: `200 OK` - Updated rental document

### **4.5 Delete Rental**
- **Endpoint**: `DELETE /api/rentals/:id`
- **Description**: Deletes rental transaction
- **Parameters**: `id` (ObjectId) - Rental ObjectId
- **MongoDB Operations**:
  - `db.rentals.deleteOne({_id: ObjectId(id)})`
  - `db.inventory.updateOne({_id: ObjectId(inventoryId)}, {$set: {status: "available"}})`
- **Response**: `204 No Content`

### **4.6 Get Rentals by Customer**
- **Endpoint**: `GET /api/rentals/customer/:customerId`
- **Description**: Retrieves all rentals for specific customer
- **Parameters**: `customerId` (ObjectId) - Customer ObjectId
- **MongoDB Query**: `db.rentals.find({customerId: ObjectId(customerId)})`
- **Response**: `200 OK` - Array of rental documents

### **4.7 Get Active Rentals by Customer**
- **Endpoint**: `GET /api/rentals/active/:customerId`
- **Description**: Retrieves active (unreturned) rentals
- **Parameters**: `customerId` (ObjectId) - Customer ObjectId
- **MongoDB Query**: `db.rentals.find({customerId: ObjectId(customerId), returnDate: {$exists: false}})`
- **Response**: `200 OK` - Array of rental documents

### **4.8 Return Rental**
- **Endpoint**: `PUT /api/rentals/:id/return`
- **Description**: Marks rental as returned
- **Parameters**: `id` (ObjectId) - Rental ObjectId
- **MongoDB Operations**:
  - `db.rentals.updateOne({_id: ObjectId(id)}, {$set: {returnDate: new Date()}})`
  - `db.inventory.updateOne({_id: ObjectId(inventoryId)}, {$set: {status: "available"}})`
- **Response**: `200 OK` - Updated rental document

---

## **5. PAYMENTS API**
**Base Path**: `/api/payments`

### **5.1 Get All Payments**
- **Endpoint**: `GET /api/payments`
- **Description**: Retrieves all payment transactions
- **MongoDB Query**: `db.payments.find({})`
- **Response**: `200 OK` - Array of payment documents

### **5.2 Get Payment by ID**
- **Endpoint**: `GET /api/payments/:id`
- **Description**: Retrieves payment by ObjectId
- **Parameters**: `id` (ObjectId) - Payment ObjectId
- **MongoDB Query**: `db.payments.findOne({_id: ObjectId(id)})`
- **Response**: `200 OK` - Payment document or `404 Not Found`

### **5.3 Create New Payment**
- **Endpoint**: `POST /api/payments`
- **Description**: Creates new payment transaction
- **Request Body**: Payment document
- **MongoDB Operations**:
  - `db.payments.insertOne(paymentData)`
  - `db.rentals.updateOne({_id: ObjectId(rentalId)}, {$set: {paymentId: ObjectId(paymentId)}})`
- **Response**: `201 Created` - Created payment document
- **Example Request Body**:
```json
{
  "customerId": "ObjectId('507f1f77bcf86cd799439011')",
  "rentalId": "ObjectId('507f1f77bcf86cd799439012')",
  "staffId": "ObjectId('507f1f77bcf86cd799439013')",
  "amount": 4.99,
  "paymentMethod": "credit_card",
  "paymentDate": "2024-01-15T10:00:00.000Z"
}
```

### **5.4 Get Payments by Customer**
- **Endpoint**: `GET /api/payments/customer/:customerId`
- **Description**: Retrieves all payments for specific customer
- **Parameters**: `customerId` (ObjectId) - Customer ObjectId
- **MongoDB Query**: `db.payments.find({customerId: ObjectId(customerId)})`
- **Response**: `200 OK` - Array of payment documents

### **5.5 Get Total Payments by Customer**
- **Endpoint**: `GET /api/payments/customer/:customerId/total`
- **Description**: Calculates total amount paid by customer
- **Parameters**: `customerId` (ObjectId) - Customer ObjectId
- **MongoDB Aggregation**: `db.payments.aggregate([{$match: {customerId: ObjectId(customerId)}}, {$group: {_id: null, total: {$sum: "$amount"}}}])`
- **Response**: `200 OK` - Total amount

---

## **6. STORES API**
**Base Path**: `/api/stores`

### **6.1 Get All Stores**
- **Endpoint**: `GET /api/stores`
- **Description**: Retrieves all store locations with manager and inventory
- **MongoDB Query**: `db.stores.find({})`
- **Response**: `200 OK` - Array of store documents

### **6.2 Get Store by ID**
- **Endpoint**: `GET /api/stores/:id`
- **Description**: Retrieves store by ObjectId
- **Parameters**: `id` (ObjectId) - Store ObjectId
- **MongoDB Query**: `db.stores.findOne({_id: ObjectId(id)})`
- **Response**: `200 OK` - Store document or `404 Not Found`

### **6.3 Create New Store**
- **Endpoint**: `POST /api/stores`
- **Description**: Creates new store location
- **Request Body**: Store document
- **MongoDB Operation**: `db.stores.insertOne(storeData)`
- **Response**: `201 Created` - Created store document
- **Example Request Body**:
```json
{
  "name": "Downtown Store",
  "address": {
    "street": "456 Downtown Ave",
    "city": "New York",
    "state": "NY",
    "postalCode": "10002",
    "country": "USA"
  },
  "managerId": "ObjectId('507f1f77bcf86cd799439011')",
  "phone": "+1-555-0123",
  "inventory": [],
  "staff": []
}
```

### **6.4 Get Store Inventory**
- **Endpoint**: `GET /api/stores/:id/inventory`
- **Description**: Retrieves inventory for specific store
- **Parameters**: `id` (ObjectId) - Store ObjectId
- **MongoDB Query**: `db.inventory.find({storeId: storeId})`
- **Response**: `200 OK` - Array of inventory documents

---

## **7. STAFF API**
**Base Path**: `/api/staff`

### **7.1 Get All Staff**
- **Endpoint**: `GET /api/staff`
- **Description**: Retrieves all staff members
- **MongoDB Query**: `db.staff.find({})`
- **Response**: `200 OK` - Array of staff documents

### **7.2 Get Staff by ID**
- **Endpoint**: `GET /api/staff/:id`
- **Description**: Retrieves staff member by ObjectId
- **Parameters**: `id` (ObjectId) - Staff ObjectId
- **MongoDB Query**: `db.staff.findOne({_id: ObjectId(id)})`
- **Response**: `200 OK` - Staff document or `404 Not Found`

### **7.3 Create New Staff**
- **Endpoint**: `POST /api/staff`
- **Description**: Creates new staff member
- **Request Body**: Staff document
- **MongoDB Operation**: `db.staff.insertOne(staffData)`
- **Response**: `201 Created` - Created staff document

### **7.4 Update Staff**
- **Endpoint**: `PUT /api/staff/:id`
- **Description**: Updates existing staff member
- **Parameters**: `id` (ObjectId) - Staff ObjectId
- **MongoDB Operation**: `db.staff.updateOne({_id: ObjectId(id)}, {$set: updateData})`
- **Response**: `200 OK` - Updated staff document

### **7.5 Get Staff by Store**
- **Endpoint**: `GET /api/staff/store/:storeId`
- **Description**: Retrieves staff for specific store
- **Parameters**: `storeId` (Integer) - Store ID
- **MongoDB Query**: `db.staff.find({storeId: storeId})`
- **Response**: `200 OK` - Array of staff documents

---

## **8. CATEGORIES API**
**Base Path**: `/api/categories`

### **8.1 Get All Categories**
- **Endpoint**: `GET /api/categories`
- **Description**: Retrieves all film categories
- **MongoDB Query**: `db.categories.find({})`
- **Response**: `200 OK` - Array of category documents

### **8.2 Get Category by ID**
- **Endpoint**: `GET /api/categories/:id`
- **Description**: Retrieves category by ObjectId
- **Parameters**: `id` (ObjectId) - Category ObjectId
- **MongoDB Query**: `db.categories.findOne({_id: ObjectId(id)})`
- **Response**: `200 OK` - Category document or `404 Not Found`

### **8.3 Create New Category**
- **Endpoint**: `POST /api/categories`
- **Description**: Creates new film category
- **Request Body**: Category document
- **MongoDB Operation**: `db.categories.insertOne(categoryData)`
- **Response**: `201 Created` - Created category document

### **8.4 Update Category**
- **Endpoint**: `PUT /api/categories/:id`
- **Description**: Updates existing category
- **Parameters**: `id` (ObjectId) - Category ObjectId
- **MongoDB Operation**: `db.categories.updateOne({_id: ObjectId(id)}, {$set: updateData})`
- **Response**: `200 OK` - Updated category document

### **8.5 Delete Category**
- **Endpoint**: `DELETE /api/categories/:id`
- **Description**: Deletes category and removes from films
- **Parameters**: `id` (ObjectId) - Category ObjectId
- **MongoDB Operations**:
  - `db.categories.deleteOne({_id: ObjectId(id)})`
  - `db.films.updateMany({}, {$pull: {categories: categoryName}})`
- **Response**: `204 No Content`

---

## **9. INVENTORY API**
**Base Path**: `/api/inventory`

### **9.1 Get All Inventory**
- **Endpoint**: `GET /api/inventory`
- **Description**: Retrieves all inventory items
- **MongoDB Query**: `db.inventory.find({})`
- **Response**: `200 OK` - Array of inventory documents

### **9.2 Get Inventory by ID**
- **Endpoint**: `GET /api/inventory/:id`
- **Description**: Retrieves inventory item by ObjectId
- **Parameters**: `id` (ObjectId) - Inventory ObjectId
- **MongoDB Query**: `db.inventory.findOne({_id: ObjectId(id)})`
- **Response**: `200 OK` - Inventory document or `404 Not Found`

### **9.3 Get Inventory by Store**
- **Endpoint**: `GET /api/inventory/store/:storeId`
- **Description**: Retrieves inventory for specific store
- **Parameters**: `storeId` (Integer) - Store ID
- **MongoDB Query**: `db.inventory.find({storeId: storeId})`
- **Response**: `200 OK` - Array of inventory documents

### **9.4 Get Available Inventory**
- **Endpoint**: `GET /api/inventory/available`
- **Description**: Retrieves available inventory items
- **MongoDB Query**: `db.inventory.find({status: "available"})`
- **Response**: `200 OK` - Array of available inventory documents

---

## **10. MONGODB-SPECIFIC FEATURES**

### **10.1 Aggregation Pipeline Examples**

#### **Sales by Film Category**
```javascript
db.rentals.aggregate([
  {
    $lookup: {
      from: "films",
      localField: "filmId",
      foreignField: "_id",
      as: "film"
    }
  },
  {
    $unwind: "$film"
  },
  {
    $group: {
      _id: "$film.categories",
      totalSales: {$sum: "$rentalRate"},
      rentalCount: {$sum: 1}
    }
  }
])
```

#### **Customer Rental History**
```javascript
db.customers.aggregate([
  {
    $lookup: {
      from: "rentals",
      localField: "_id",
      foreignField: "customerId",
      as: "rentals"
    }
  },
  {
    $project: {
      name: {$concat: ["$firstName", " ", "$lastName"]},
      rentalCount: {$size: "$rentals"},
      totalSpent: {$sum: "$rentals.rentalRate"}
    }
  }
])
```

### **10.2 Indexing Strategy**
```javascript
// Text index for film search
db.films.createIndex({title: "text", description: "text"})

// Compound indexes for common queries
db.rentals.createIndex({customerId: 1, rentalDate: -1})
db.inventory.createIndex({storeId: 1, status: 1})
db.films.createIndex({rating: 1, releaseYear: -1})

// Unique indexes
db.customers.createIndex({email: 1}, {unique: true})
db.staff.createIndex({username: 1}, {unique: true})
```

### **10.3 Data Validation**
```javascript
// Film schema validation
db.runCommand({
  collMod: "films",
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["title", "language", "rentalRate"],
      properties: {
        title: {bsonType: "string", minLength: 1},
        rentalRate: {bsonType: "number", minimum: 0},
        rating: {enum: ["G", "PG", "PG-13", "R", "NC-17"]}
      }
    }
  }
})
```

---

## **11. RESPONSE FORMATS & ERROR HANDLING**

### **11.1 Success Response Format**
```json
{
  "success": true,
  "data": {...},
  "message": "Operation completed successfully",
  "timestamp": "2024-01-15T10:30:00.000Z"
}
```

### **11.2 Error Response Format**
```json
{
  "success": false,
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Invalid input data",
    "details": ["Title is required", "Rental rate must be positive"]
  },
  "timestamp": "2024-01-15T10:30:00.000Z"
}
```

### **11.3 HTTP Status Codes**
- **200 OK**: Successful GET, PUT operations
- **201 Created**: Successful POST operations
- **204 No Content**: Successful DELETE operations
- **400 Bad Request**: Validation errors, invalid data
- **404 Not Found**: Resource not found
- **409 Conflict**: Duplicate data, constraint violations
- **500 Internal Server Error**: Server-side errors

---

## **12. USAGE EXAMPLES**

### **12.1 Complete Film Management Workflow**
```bash
# 1. Create a new film
POST /api/films
{
  "title": "The Shawshank Redemption",
  "description": "Two imprisoned men bond over a number of years...",
  "releaseYear": 1994,
  "language": "English",
  "rentalRate": 4.99,
  "categories": ["Drama"],
  "actors": [
    {"name": "Tim Robbins", "role": "Andy Dufresne"},
    {"name": "Morgan Freeman", "role": "Ellis Boyd"}
  ]
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

### **12.2 Customer Rental Workflow**
```bash
# 1. Create a customer
POST /api/customers
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@email.com",
  "storeId": 1,
  "address": {
    "street": "123 Main St",
    "city": "New York",
    "state": "NY",
    "postalCode": "10001"
  }
}

# 2. Create a rental
POST /api/rentals
{
  "customerId": "{customerId}",
  "filmId": "{filmId}",
  "inventoryId": "{inventoryId}",
  "staffId": "{staffId}",
  "rentalDate": "2024-01-15T10:00:00.000Z"
}

# 3. Create payment
POST /api/payments
{
  "customerId": "{customerId}",
  "rentalId": "{rentalId}",
  "amount": 4.99,
  "paymentMethod": "credit_card"
}

# 4. Return the rental
PUT /api/rentals/{rentalId}/return
```

---

## **13. PERFORMANCE OPTIMIZATION**

### **13.1 Database Optimization**
- **Indexing**: Strategic indexes for common query patterns
- **Aggregation**: Use aggregation pipelines for complex queries
- **Projection**: Select only required fields
- **Pagination**: Implement cursor-based pagination for large datasets

### **13.2 Application Optimization**
- **Connection Pooling**: Efficient MongoDB connection management
- **Caching**: Redis for frequently accessed data
- **Async Operations**: Non-blocking I/O operations
- **Batch Operations**: Bulk insert/update operations

---

## **14. SECURITY CONSIDERATIONS**

### **14.1 Input Validation**
- **Schema Validation**: MongoDB schema validation
- **Sanitization**: Input sanitization and escaping
- **Type Checking**: Proper data type validation

### **14.2 Authentication & Authorization**
- **JWT Tokens**: Secure authentication
- **Role-Based Access**: Different permissions for different user types
- **API Keys**: Secure external API access

---

## **15. MONITORING & LOGGING**

### **15.1 MongoDB Monitoring**
- **Query Performance**: Monitor slow queries
- **Connection Status**: Track connection pool usage
- **Index Usage**: Monitor index effectiveness

### **15.2 Application Monitoring**
- **Response Times**: API endpoint performance
- **Error Rates**: Track error frequencies
- **Resource Usage**: Memory and CPU monitoring

---

This API documentation provides a comprehensive guide to the MongoDB + Node.js implementation of the DVD Rental Management System, including MongoDB-specific features, aggregation pipelines, and NoSQL best practices.
