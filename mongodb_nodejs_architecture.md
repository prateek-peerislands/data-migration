# MongoDB + Node.js Architecture for DVD Rental System

## **MongoDB + Node.js Architecture Diagram**

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                           MONGODB DATABASE LAYER                               │
│                           (Document-Oriented)                                  │
├─────────────────────────────────────────────────────────────────────────────────┤
│                                                                                 │
│  ┌─────────────────────────────────────────────────────────────────────────┐   │
│  │                           COLLECTIONS                                   │   │
│  │                                                                         │   │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐ │ │
│  │  │   films     │    │   actors    │    │  categories │    │  languages  │ │ │
│  │  │             │    │             │    │             │    │             │ │ │
│  │  │ {           │    │ {           │    │ {           │    │ {           │ │ │
│  │  │   _id:      │    │   _id:      │    │   _id:      │    │   _id:      │ │ │
│  │  │   title:    │    │   name:     │    │   name:     │    │   name:     │ │ │
│  │  │   rating:   │    │   bio:      │    │   desc:     │    │   code:     │ │ │
│  │  │   actors:   │    │   films:    │    │   films:    │    │   films:    │ │ │
│  │  │   categories│    │   [filmIds] │    │   [filmIds] │    │   [filmIds] │ │ │
│  │  │ }           │    │ }           │    │ }           │    │ }           │ │ │
│  │  └─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘ │ │
│  │                                                                         │   │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐ │ │
│  │  │   stores    │    │   staff     │    │  customers  │    │  rentals    │ │ │
│  │  │             │    │             │    │             │    │             │ │ │
│  │  │ {           │    │ {           │    │ {           │    │ {           │ │ │
│  │  │   _id:      │    │   _id:      │    │   _id:      │    │   _id:      │ │ │
│  │  │   name:     │    │   username: │    │   email:    │    │   customerId│ │ │
│  │  │   address:  │    │   password: │    │   address:  │    │   filmId:   │ │ │
│  │  │   manager:  │    │   storeId:  │    │   storeId:  │    │   rentalDate│ │ │
│  │  │   inventory:│    │   role:     │    │   rentals:  │    │   returnDate│ │ │
│  │  │   [filmIds] │    │ }           │    │   [rentalIds]│   │   payment:  │ │ │
│  │  │ }           │    │             │    │ }           │    │   {amount,  │ │ │
│  │  └─────────────┘    └─────────────┘    └─────────────┘    │   date}     │ │ │
│  │                                                                         │   │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐    └─────────────┘ │ │
│  │  │  inventory  │    │  payments   │    │   addresses │    │             │ │ │
│  │  │             │    │             │    │             │    │             │ │ │
│  │  │ {           │    │ {           │    │ {           │    │             │ │ │
│  │  │   _id:      │    │   _id:      │    │   _id:      │    │             │ │ │
│  │  │   filmId:   │    │   rentalId: │    │   street:   │    │             │ │ │
│  │  │   storeId:  │    │   amount:   │    │   city:     │    │             │ │ │
│  │  │   status:   │    │   date:     │    │   country:  │    │             │ │ │
│  │  │   condition:│    │   method:   │    │   postalCode│    │             │ │ │
│  │  │ }           │    │ }           │    │ }           │    │             │ │ │
│  │  └─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘ │ │
│  └─────────────────────────────────────────────────────────────────────────┘   │
│                                                                                 │
│  ┌─────────────────────────────────────────────────────────────────────────┐   │
│  │                        INDEXES & AGGREGATIONS                           │   │
│  │  • film.title (text index)                                              │   │
│  │  • film.rating (single field)                                           │   │
│  │  • film.releaseYear (single field)                                      │   │
│  │  • rental.customerId (single field)                                     │   │
│  │  • rental.filmId (single field)                                         │   │
│  │  • compound indexes for complex queries                                  │   │
│  └─────────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    │ MongoDB Driver
                                    │ (mongodb://localhost:27017)
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────┐
│                           NODE.JS APPLICATION LAYER                            │
│                           (Port 3000)                                         │
├─────────────────────────────────────────────────────────────────────────────────┤
│                                                                                 │
│  ┌─────────────────────────────────────────────────────────────────────────┐   │
│  │                        MAIN APPLICATION FILE                            │   │
│  │                           server.js                                      │   │
│  │                    - Express.js Server Setup                           │   │
│  │                    - MongoDB Connection                                 │   │
│  │                    - Middleware Configuration                           │   │
│  │                    - Route Registration                                 │   │
│  └─────────────────────────────────────────────────────────────────────────┘   │
│                                    │                                           │
│                                    │ Module Imports                            │
│                                    ▼                                           │
│  ┌─────────────────────────────────────────────────────────────────────────┐   │
│  │                        CONFIGURATION LAYER                              │   │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐                  │   │
│  │  │  config/    │    │  .env       │    │  middleware │                  │   │
│  │  │ database.js │    │  (Environment│   │  (CORS,     │                  │   │
│  │  │ (MongoDB    │    │   Variables) │   │   Auth,     │                  │   │
│  │  │  Connection)│    │             │    │   Validation)│                  │   │
│  │  └─────────────┘    └─────────────┘    └─────────────┘                  │   │
│  └─────────────────────────────────────────────────────────────────────────┘   │
│                                    │                                           │
│                                    │ Express Router                            │
│                                    ▼                                           │
│  ┌─────────────────────────────────────────────────────────────────────────┐   │
│  │                           ROUTES LAYER                                  │   │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐ │ │
│  │  │routes/       │    │routes/      │    │routes/      │    │routes/      │ │ │
│  │  │films.js      │    │actors.js    │    │customers.js │    │rentals.js   │ │ │
│  │  │             │    │             │    │             │    │             │ │ │
│  │  │GET /films   │    │GET /actors  │    │GET /customers│   │GET /rentals │ │ │
│  │  │POST /films  │    │POST /actors │    │POST /customers│  │POST /rentals│ │ │
│  │  │PUT /films/:id│   │PUT /actors/:id│  │PUT /customers/:id│PUT /rentals/:id│ │
│  │  │DELETE /films/:id│DELETE /actors/:id│DELETE /customers/:id│DELETE /rentals/:id│ │
│  │  └─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘ │ │
│  │         │                   │                   │                   │     │ │
│  │         │                   │                   │                   │     │ │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐ │ │
│  │  │routes/       │    │routes/      │    │routes/      │    │routes/      │ │ │
│  │  │stores.js     │    │staff.js     │    │payments.js  │    │categories.js│ │ │
│  │  │             │    │             │    │             │    │             │ │ │
│  │  │GET /stores  │    │GET /staff   │    │GET /payments│   │GET /categories│ │ │
│  │  │POST /stores │    │POST /staff  │    │POST /payments│   │POST /categories│ │ │
│  │  │PUT /stores/:id│  │PUT /staff/:id│   │PUT /payments/:id│PUT /categories/:id│ │
│  │  │DELETE /stores/:id│DELETE /staff/:id│DELETE /payments/:id│DELETE /categories/:id│ │
│  │  └─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘ │ │
│  └─────────────────────────────────────────────────────────────────────────┘   │
│                                    │                                           │
│                                    │ Controller Calls                          │
│                                    ▼                                           │
│  ┌─────────────────────────────────────────────────────────────────────────┐   │
│  │                         CONTROLLER LAYER                                │   │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐ │ │
│  │  │controllers/ │    │controllers/ │    │controllers/ │    │controllers/ │ │ │
│  │  │filmController│   │actorController│  │customerController│rentalController│ │ │
│  │  │             │    │             │    │             │    │             │ │ │
│  │  │getAllFilms()│    │getAllActors()│   │getAllCustomers()│getAllRentals()│ │ │
│  │  │getFilmById()│    │getActorById()│   │getCustomerById()│getRentalById()│ │ │
│  │  │createFilm() │    │createActor() │   │createCustomer()│createRental() │ │ │
│  │  │updateFilm() │    │updateActor() │   │updateCustomer()│updateRental() │ │ │
│  │  │deleteFilm() │    │deleteActor() │   │deleteCustomer()│deleteRental() │ │ │
│  │  └─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘ │ │
│  │         │                   │                   │                   │     │ │
│  │         │                   │                   │                   │     │ │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐ │ │
│  │  │controllers/ │    │controllers/ │    │controllers/ │    │controllers/ │ │ │
│  │  │storeController│  │staffController│  │paymentController│categoryController│ │ │
│  │  │             │    │             │    │             │    │             │ │ │
│  │  └─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘ │ │
│  └─────────────────────────────────────────────────────────────────────────┘   │
│                                    │                                           │
│                                    │ Service Calls                             │
│                                    ▼                                           │
│  ┌─────────────────────────────────────────────────────────────────────────┐   │
│  │                           SERVICE LAYER                                  │   │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐ │ │
│  │  │services/    │    │services/    │    │services/    │    │services/    │ │ │
│  │  │filmService.js│   │actorService.js│  │customerService.js│rentalService.js│ │ │
│  │  │             │    │             │    │             │    │             │ │ │
│  │  │findAll()    │    │findAll()    │    │findAll()    │    │findAll()    │ │ │
│  │  │findById()   │    │findById()   │    │findById()   │    │findById()   │ │ │
│  │  │create()     │    │create()     │    │create()     │    │create()     │ │ │
│  │  │update()     │    │update()     │    │update()     │    │update()     │ │ │
│  │  │delete()     │    │delete()     │    │delete()     │    │delete()     │ │ │
│  │  │search()     │    │search()     │    │search()     │    │search()     │ │ │
│  │  └─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘ │ │
│  │         │                   │                   │                   │     │ │
│  │         │                   │                   │                   │     │ │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐ │ │
│  │  │services/    │    │services/    │    │services/    │    │services/    │ │ │
│  │  │storeService.js│  │staffService.js│  │paymentService.js│categoryService.js│ │ │
│  │  │             │    │             │    │             │    │             │ │ │
│  │  └─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘ │ │
│  └─────────────────────────────────────────────────────────────────────────┘   │
│                                    │                                           │
│                                    │ Model Calls                               │
│                                    ▼                                           │
│  ┌─────────────────────────────────────────────────────────────────────────┐   │
│  │                            MODEL LAYER                                   │   │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐ │ │
│  │  │models/      │    │models/      │    │models/      │    │models/      │ │ │
│  │  │Film.js      │    │Actor.js     │    │Customer.js  │    │Rental.js    │ │ │
│  │  │             │    │             │    │             │    │             │ │ │
│  │  │const filmSchema│ │const actorSchema│ │const customerSchema│const rentalSchema│ │ │
│  │  │= new Schema({│  │= new Schema({│   │= new Schema({│   │= new Schema({│ │ │
│  │  │  title: String,│ │  name: String,│  │  email: String,│  │  customerId: ObjectId,│ │ │
│  │  │  rating: String,│ │  bio: String,│   │  address: Object,│  │  filmId: ObjectId,│ │ │
│  │  │  actors: [ObjectId],│ │  films: [ObjectId],│ │  storeId: ObjectId,│  │  rentalDate: Date,│ │ │
│  │  │  categories: [ObjectId]│ │  });        │  │  rentals: [ObjectId]│  │  returnDate: Date,│ │ │
│  │  │});         │    │             │    │  });         │   │  payment: Object│ │ │
│  │  └─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘ │ │
│  │         │                   │                   │                   │     │ │
│  │         │                   │                   │                   │     │ │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐ │ │
│  │  │models/      │    │models/      │    │models/      │    │models/      │ │ │
│  │  │Store.js     │    │Staff.js     │    │Payment.js   │    │Category.js  │ │ │
│  │  │             │    │             │    │             │    │             │ │ │
│  │  └─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘ │ │
│  └─────────────────────────────────────────────────────────────────────────┘   │
│                                    │                                           │
│                                    │ MongoDB Operations                        │ │
│                                    ▼                                           │
│  ┌─────────────────────────────────────────────────────────────────────────┐   │
│  │                        DATABASE OPERATIONS                               │   │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐ │ │
│  │  │MongoDB      │    │Aggregation │    │Indexing     │    │Validation   │ │ │
│  │  │Operations   │    │Pipelines   │    │             │    │             │ │ │
│  │  │             │    │             │    │             │    │             │ │ │
│  │  │• find()     │    │• $lookup    │    │• Text Index │    │• Schema     │ │ │
│  │  │• insertOne()│    │• $match     │    │• Compound   │    │• Custom     │ │ │
│  │  │• updateOne()│    │• $group     │    │• Unique     │    │• Middleware │ │ │
│  │  │• deleteOne()│    │• $sort      │    │• Sparse     │    │             │ │ │
│  │  │• count()    │    │• $project   │    │             │    │             │ │ │
│  │  └─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘ │ │
│  └─────────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    │ HTTP REST API
                                    │ (JSON)
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────┐
│                              FRONTEND/CLIENT LAYER                             │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐     │
│  │database-    │    │   index.html│    │mcp-backup.  │    │   Other     │     │
│  │interface.   │    │ (Main Page) │    │html         │    │   Clients   │     │
│  │html         │    │             │    │(MCP Tools)  │    │             │     │
│  │             │    │             │    │             │    │             │     │
│  └─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘     │
└─────────────────────────────────────────────────────────────────────────────────┘
```

## **Key Differences from PostgreSQL + Spring Boot:**

### **1. Database Structure**
- **Collections** instead of tables
- **Documents** instead of rows
- **Embedded objects** instead of joins
- **Flexible schema** with dynamic fields
- **NoSQL** approach vs relational

### **2. Application Architecture**
- **Node.js** runtime instead of JVM
- **Express.js** framework instead of Spring Boot
- **MongoDB driver** instead of JPA/Hibernate
- **JavaScript/TypeScript** instead of Java
- **Event-driven** vs thread-based

### **3. Data Modeling Approach**
- **Denormalized** data for performance
- **Embedded documents** for related data
- **Array fields** for one-to-many relationships
- **ObjectId references** for complex relationships

### **4. Technology Stack**
- **Runtime**: Node.js (V8 engine)
- **Framework**: Express.js
- **Database**: MongoDB
- **Language**: JavaScript/TypeScript
- **Package Manager**: npm/yarn

### **5. Development Benefits**
✅ **Faster development** with JavaScript ecosystem  
✅ **Flexible schema** for rapid prototyping  
✅ **Horizontal scaling** with MongoDB sharding  
✅ **Rich ecosystem** of npm packages  
✅ **JSON-native** data handling  

### **6. File Structure Example**
```
project/
├── server.js              # Main application entry point
├── package.json           # Dependencies and scripts
├── .env                   # Environment variables
├── config/
│   └── database.js        # MongoDB connection
├── routes/
│   ├── films.js           # Film API routes
│   ├── actors.js          # Actor API routes
│   └── customers.js       # Customer API routes
├── controllers/
│   ├── filmController.js  # Film business logic
│   ├── actorController.js # Actor business logic
│   └── customerController.js # Customer business logic
├── services/
│   ├── filmService.js     # Film data operations
│   ├── actorService.js    # Actor data operations
│   └── customerService.js # Customer data operations
├── models/
│   ├── Film.js            # Film schema definition
│   ├── Actor.js           # Actor schema definition
│   └── Customer.js        # Customer schema definition
└── middleware/
    ├── auth.js            # Authentication middleware
    ├── validation.js      # Request validation
    └── errorHandler.js    # Error handling
```

This architecture provides a modern, scalable alternative to traditional relational databases with a focus on flexibility, performance, and rapid development.
