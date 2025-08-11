# DVD Rental Database Schema Analysis

## Overview
Your PostgreSQL database contains a comprehensive DVD rental management system with **22 tables** including base tables, junction tables, and reporting views. This is a well-designed, production-ready schema that follows normalization principles.

## Database Structure

### **Base Tables (9 tables)**
These are the core transactional tables that store the primary business data:

#### 1. **Film Management**
- **`film`** - Central entity containing movie information
  - **Key Fields**: `film_id` (PK), `title`, `description`, `rental_rate`, `replacement_cost`
  - **Special Features**: `rating` (MPAA), `special_features` (array), `fulltext` (search)
  - **Business Logic**: Default rental duration (3 days), default rental rate ($4.99)

- **`language`** - Supported languages for films
- **`category`** - Film genres/categories
- **`actor`** - Cast member information

#### 2. **Store Operations**
- **`store`** - Physical store locations
  - **Key Fields**: `store_id` (PK), `manager_staff_id` (FK to staff)
  - **Business Rule**: Each store has exactly one manager

- **`staff`** - Store employees
  - **Key Fields**: `staff_id` (PK), `username`, `password`, `store_id`
  - **Features**: Profile pictures (`bytea`), authentication credentials

- **`inventory`** - Physical copies of films
  - **Key Fields**: `inventory_id` (PK), `film_id`, `store_id`
  - **Business Logic**: Tracks individual copies per store location

#### 3. **Customer & Rental Management**
- **`customer`** - Rental customers
  - **Key Fields**: `customer_id` (PK), `store_id`, `email`, `address_id`
  - **Status Tracking**: `activebool` and `active` fields for account status

- **`rental`** - Film rental transactions
  - **Key Fields**: `rental_id` (PK), `rental_date`, `return_date`, `customer_id`
  - **Business Logic**: Tracks when films are rented and returned

- **`payment`** - Financial transactions
  - **Key Fields**: `payment_id` (PK), `amount`, `rental_id`
  - **Relationship**: One-to-one with rental (each rental generates one payment)

#### 4. **Address Management**
- **`address`** - Street addresses with contact information
- **`city`** - City information linked to countries
- **`country`** - Geographic hierarchy

### **Junction Tables (2 tables)**
These handle many-to-many relationships:

- **`film_actor`** - Links films to actors (M:N relationship)
- **`film_category`** - Links films to categories (M:N relationship)

### **View Tables (11 tables)**
These provide business intelligence and reporting:

- **`actor_info`** - Actor details with film information
- **`customer_list`** - Customer details with full address
- **`film_list`** - Film details with category and actor information
- **`staff_list`** - Staff details with address information
- **`sales_by_film_category`** - Revenue by film category
- **`sales_by_store`** - Revenue by store location
- **`nicer_but_slower_film_list`** - Alternative film listing

## Key Relationships & Business Rules

### **1. Film Management**
```
FILM (1) ←→ (M) INVENTORY
FILM (M) ←→ (M) ACTOR (via film_actor)
FILM (M) ←→ (M) CATEGORY (via film_category)
FILM (N) ←→ (1) LANGUAGE
```

**Business Rules:**
- Each film can have multiple actors
- Each film can belong to multiple categories
- Each film has exactly one language
- Films are distributed across multiple stores as inventory

### **2. Store Operations**
```
STORE (1) ←→ (M) STAFF
STORE (1) ←→ (M) CUSTOMER
STORE (1) ←→ (M) INVENTORY
STORE (1) ←→ (1) STAFF (manager)
```

**Business Rules:**
- Each store has one manager (staff member)
- Stores employ multiple staff members
- Stores serve multiple customers
- Stores stock multiple inventory items

### **3. Rental Process**
```
CUSTOMER (1) ←→ (M) RENTAL
STAFF (1) ←→ (M) RENTAL
INVENTORY (1) ←→ (M) RENTAL
RENTAL (1) ←→ (1) PAYMENT
```

**Business Rules:**
- Customers can make multiple rentals
- Staff process multiple rentals
- Each rental generates exactly one payment
- Inventory items can be rented multiple times

### **4. Address Hierarchy**
```
COUNTRY (1) ←→ (M) CITY
CITY (1) ←→ (M) ADDRESS
ADDRESS (1) ←→ (M) CUSTOMER/STAFF/STORE
```

**Business Rules:**
- Geographic data is normalized for consistency
- Addresses are shared between customers, staff, and stores

## Data Types & Constraints

### **Primary Keys**
- All major entities use auto-incrementing integer primary keys
- Sequences are automatically generated for ID fields

### **Foreign Keys**
- Comprehensive referential integrity across all tables
- Cascade rules maintain data consistency

### **Special Data Types**
- **`mpaa_rating`** - Custom enum for film ratings (G, PG, R, etc.)
- **`tsvector`** - Full-text search capabilities on film descriptions
- **`ARRAY`** - Special features stored as arrays
- **`bytea`** - Binary data for staff pictures
- **`numeric`** - Precise decimal for monetary values

### **Audit Fields**
- **`last_update`** - Timestamp tracking on all major tables
- **`create_date`** - Customer registration dates
- **`rental_date`/`return_date`** - Rental transaction timing

## Business Intelligence Features

### **Reporting Views**
The database includes pre-built views for common business queries:
- Sales analysis by category and store
- Customer and staff contact information
- Film availability and details
- Revenue tracking and reporting

### **Search Capabilities**
- Full-text search on film descriptions (`fulltext` field)
- Categorized film browsing
- Actor and category filtering

## Schema Quality Assessment

### **Strengths**
✅ **Normalized Design** - Follows 3NF principles
✅ **Comprehensive Coverage** - Handles all aspects of rental business
✅ **Audit Trail** - Complete timestamp tracking
✅ **Business Intelligence** - Pre-built reporting views
✅ **Data Integrity** - Strong foreign key relationships
✅ **Scalability** - Supports multiple stores and users

### **Design Patterns**
- **Junction Tables** for M:N relationships
- **Hierarchical Address** structure
- **Store-Centric** customer and staff management
- **Inventory Tracking** per store location
- **Audit Fields** for compliance and debugging

This schema represents a mature, enterprise-grade database design that could support a real DVD rental business with multiple locations, comprehensive reporting, and robust data integrity.
