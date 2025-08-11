# MongoDB Schema Design for DVD Rental Management System

## Overview
This document outlines the MongoDB schema design that corresponds to the PostgreSQL DVD Rental Management System. The design leverages MongoDB's document-oriented structure while maintaining the same business logic and relationships.

## Collection Design Strategy

### **1. Core Business Collections**

#### **films Collection**
```json
{
  "_id": ObjectId("..."),
  "title": "The Matrix",
  "description": "A computer hacker learns from mysterious rebels about the true nature of his reality.",
  "releaseYear": 1999,
  "language": {
    "_id": ObjectId("..."),
    "name": "English"
  },
  "rentalDuration": 3,
  "rentalRate": 4.99,
  "length": 136,
  "replacementCost": 19.99,
  "rating": "R",
  "specialFeatures": ["Trailers", "Commentaries", "Behind the Scenes"],
  "fulltext": "matrix computer hacker reality",
  "lastUpdate": ISODate("2024-01-15T10:30:00Z"),
  "categories": [
    {
      "_id": ObjectId("..."),
      "name": "Action"
    },
    {
      "_id": ObjectId("..."),
      "name": "Sci-Fi"
    }
  ],
  "actors": [
    {
      "_id": ObjectId("..."),
      "firstName": "Keanu",
      "lastName": "Reeves"
    },
    {
      "_id": ObjectId("..."),
      "firstName": "Laurence",
      "lastName": "Fishburne"
    }
  ]
}
```

#### **inventory Collection**
```json
{
  "_id": ObjectId("..."),
  "filmId": ObjectId("..."),
  "storeId": ObjectId("..."),
  "status": "available", // available, rented, lost, damaged
  "lastUpdate": ISODate("2024-01-15T10:30:00Z"),
  "film": {
    "title": "The Matrix",
    "rentalRate": 4.99,
    "replacementCost": 19.99
  },
  "store": {
    "name": "Downtown Store",
    "address": "123 Main St"
  }
}
```

#### **rentals Collection**
```json
{
  "_id": ObjectId("..."),
  "rentalDate": ISODate("2024-01-15T10:30:00Z"),
  "returnDate": ISODate("2024-01-18T10:30:00Z"),
  "dueDate": ISODate("2024-01-18T10:30:00Z"),
  "inventoryId": ObjectId("..."),
  "customerId": ObjectId("..."),
  "staffId": ObjectId("..."),
  "status": "active", // active, returned, overdue
  "lastUpdate": ISODate("2024-01-15T10:30:00Z"),
  "customer": {
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@email.com"
  },
  "staff": {
    "firstName": "Jane",
    "lastName": "Smith"
  },
  "film": {
    "title": "The Matrix",
    "rentalRate": 4.99
  },
  "store": {
    "name": "Downtown Store"
  }
}
```

#### **payments Collection**
```json
{
  "_id": ObjectId("..."),
  "customerId": ObjectId("..."),
  "staffId": ObjectId("..."),
  "rentalId": ObjectId("..."),
  "amount": 4.99,
  "paymentDate": ISODate("2024-01-15T10:30:00Z"),
  "paymentMethod": "cash", // cash, credit_card, debit_card
  "customer": {
    "firstName": "John",
    "lastName": "Doe"
  },
  "staff": {
    "firstName": "Jane",
    "lastName": "Smith"
  },
  "rental": {
    "filmTitle": "The Matrix",
    "rentalDate": ISODate("2024-01-15T10:30:00Z")
  }
}
```

### **2. People & Organization Collections**

#### **customers Collection**
```json
{
  "_id": ObjectId("..."),
  "storeId": ObjectId("..."),
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@email.com",
  "active": true,
  "createDate": ISODate("2023-01-15T00:00:00Z"),
  "lastUpdate": ISODate("2024-01-15T10:30:00Z"),
  "address": {
    "street": "123 Oak Street",
    "street2": "Apt 4B",
    "district": "Downtown",
    "city": "New York",
    "state": "NY",
    "postalCode": "10001",
    "country": "USA",
    "phone": "555-123-4567"
  },
  "store": {
    "name": "Downtown Store",
    "address": "123 Main St"
  },
  "rentalHistory": [
    {
      "rentalId": ObjectId("..."),
      "filmTitle": "The Matrix",
      "rentalDate": ISODate("2024-01-15T10:30:00Z"),
      "returnDate": ISODate("2024-01-18T10:30:00Z")
    }
  ],
  "totalRentals": 15,
  "totalSpent": 74.85
}
```

#### **staff Collection**
```json
{
  "_id": ObjectId("..."),
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane.smith@dvdstore.com",
  "active": true,
  "username": "jsmith",
  "password": "hashed_password_here",
  "role": "manager", // manager, clerk, assistant
  "lastUpdate": ISODate("2024-01-15T10:30:00Z"),
  "address": {
    "street": "456 Pine Avenue",
    "city": "New York",
    "state": "NY",
    "postalCode": "10002",
    "country": "USA",
    "phone": "555-987-6543"
  },
  "store": {
    "_id": ObjectId("..."),
    "name": "Downtown Store"
  },
  "workSchedule": {
    "monday": {"start": "09:00", "end": "17:00"},
    "tuesday": {"start": "09:00", "end": "17:00"},
    "wednesday": {"start": "09:00", "end": "17:00"},
    "thursday": {"start": "09:00", "end": "17:00"},
    "friday": {"start": "09:00", "end": "17:00"}
  },
  "performance": {
    "totalTransactions": 1250,
    "totalRevenue": 6234.50,
    "customerSatisfaction": 4.8
  }
}
```

#### **stores Collection**
```json
{
  "_id": ObjectId("..."),
  "name": "Downtown Store",
  "managerStaffId": ObjectId("..."),
  "address": {
    "street": "123 Main Street",
    "city": "New York",
    "state": "NY",
    "postalCode": "10001",
    "country": "USA",
    "phone": "555-111-2222"
  },
  "lastUpdate": ISODate("2024-01-15T10:30:00Z"),
  "manager": {
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane.smith@dvdstore.com"
  },
  "staff": [
    {
      "_id": ObjectId("..."),
      "firstName": "Jane",
      "lastName": "Smith",
      "role": "manager"
    },
    {
      "_id": ObjectId("..."),
      "firstName": "Mike",
      "lastName": "Johnson",
      "role": "clerk"
    }
  ],
  "inventory": {
    "totalItems": 2500,
    "availableItems": 1800,
    "rentedItems": 700
  },
  "performance": {
    "monthlyRevenue": 15420.75,
    "monthlyRentals": 1250,
    "customerCount": 450
  }
}
```

### **3. Reference Collections**

#### **actors Collection**
```json
{
  "_id": ObjectId("..."),
  "firstName": "Keanu",
  "lastName": "Reeves",
  "lastUpdate": ISODate("2024-01-15T10:30:00Z"),
  "biography": "Canadian actor known for...",
  "filmography": [
    {
      "filmId": ObjectId("..."),
      "title": "The Matrix",
      "role": "Neo",
      "year": 1999
    }
  ],
  "totalFilms": 45,
  "popularity": 9.2
}
```

#### **categories Collection**
```json
{
  "_id": ObjectId("..."),
  "name": "Action",
  "lastUpdate": ISODate("2024-01-15T10:30:00Z"),
  "description": "High-energy films with exciting sequences",
  "filmCount": 125,
  "popularity": 8.7,
  "averageRentalRate": 4.99
}
```

#### **languages Collection**
```json
{
  "_id": ObjectId("..."),
  "name": "English",
  "lastUpdate": ISODate("2024-01-15T10:30:00Z"),
  "filmCount": 850,
  "isOriginal": true
}
```

### **4. Geographic Collections**

#### **cities Collection**
```json
{
  "_id": ObjectId("..."),
  "name": "New York",
  "countryId": ObjectId("..."),
  "lastUpdate": ISODate("2024-01-15T10:30:00Z"),
  "country": {
    "name": "USA"
  },
  "storeCount": 3,
  "customerCount": 1250,
  "staffCount": 25
}
```

#### **countries Collection**
```json
{
  "_id": ObjectId("..."),
  "name": "USA",
  "lastUpdate": ISODate("2024-01-15T10:30:00Z"),
  "storeCount": 15,
  "customerCount": 8500,
  "staffCount": 120
}
```

## **Key Design Decisions & Benefits**

### **1. Embedded vs. Referenced Documents**

#### **Embedded (Denormalized):**
- **Customer addresses** - Frequently accessed, rarely changed
- **Film details in rentals** - Reduces joins for common queries
- **Store information in staff/customers** - Eliminates need for separate lookups

#### **Referenced (Normalized):**
- **Film IDs in inventory** - Large collections, frequent updates
- **Customer IDs in rentals** - Many-to-many relationships
- **Staff IDs in stores** - Complex relationships

### **2. Indexing Strategy**

```javascript
// Primary indexes
db.films.createIndex({ "title": 1 })
db.customers.createIndex({ "email": 1 })
db.rentals.createIndex({ "customerId": 1, "status": 1 })
db.inventory.createIndex({ "filmId": 1, "storeId": 1 })

// Compound indexes for common queries
db.rentals.createIndex({ "rentalDate": -1, "status": 1 })
db.payments.createIndex({ "paymentDate": -1, "customerId": 1 })
db.films.createIndex({ "categories.name": 1, "rating": 1 })

// Text search index
db.films.createIndex({ "fulltext": "text" })

// Geospatial index (if adding coordinates)
db.stores.createIndex({ "address.location": "2dsphere" })
```

### **3. Aggregation Pipeline Examples**

#### **Sales by Film Category:**
```javascript
db.rentals.aggregate([
  { $match: { status: "returned" } },
  { $lookup: { from: "films", localField: "filmId", foreignField: "_id", as: "film" } },
  { $unwind: "$film" },
  { $unwind: "$film.categories" },
  { $group: { 
    _id: "$film.categories.name", 
    totalSales: { $sum: "$film.rentalRate" },
    rentalCount: { $sum: 1 }
  }},
  { $sort: { totalSales: -1 } }
])
```

#### **Customer Rental History:**
```javascript
db.customers.aggregate([
  { $match: { _id: ObjectId("customer_id_here") } },
  { $lookup: { from: "rentals", localField: "_id", foreignField: "customerId", as: "rentals" } },
  { $project: {
    firstName: 1,
    lastName: 1,
    totalRentals: { $size: "$rentals" },
    totalSpent: { $sum: "$rentals.film.rentalRate" },
    favoriteCategories: { $addToSet: "$rentals.film.categories.name" }
  }}
])
```

## **Migration Considerations**

### **1. Data Transformation**
- **PostgreSQL arrays** → **MongoDB arrays** (special_features)
- **PostgreSQL timestamps** → **MongoDB ISODate**
- **PostgreSQL sequences** → **MongoDB ObjectId**
- **PostgreSQL foreign keys** → **MongoDB ObjectId references**

### **2. Performance Optimizations**
- **Embed frequently accessed data** to reduce lookups
- **Use compound indexes** for common query patterns
- **Implement read preferences** for read-heavy operations
- **Use aggregation pipelines** for complex analytics

### **3. Business Logic Preservation**
- **Rental duration calculations** remain the same
- **Pricing logic** unchanged
- **Status tracking** maintained
- **Audit trails** preserved with timestamps

## **Benefits of MongoDB Schema**

### **1. Flexibility**
- **Schema evolution** without migrations
- **Dynamic fields** for future requirements
- **Embedded documents** for complex data structures

### **2. Performance**
- **Reduced joins** through embedded documents
- **Efficient queries** with compound indexes
- **Horizontal scaling** capabilities

### **3. Developer Experience**
- **JSON-like documents** familiar to developers
- **Rich query language** with aggregation pipelines
- **Schema validation** for data integrity

This MongoDB schema design maintains all the business logic of your PostgreSQL system while leveraging MongoDB's strengths for document-oriented data management and horizontal scalability.
