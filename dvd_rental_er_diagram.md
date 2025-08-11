# DVD Rental Management System - ER Diagram

## Entity Relationship Diagram

```mermaid
erDiagram
    %% Core Business Entities
    FILM {
        int film_id PK
        varchar title
        text description
        int release_year
        smallint language_id FK
        smallint rental_duration
        numeric rental_rate
        smallint length
        numeric replacement_cost
        mpaa_rating rating
        timestamp last_update
        array special_features
        tsvector fulltext
    }

    INVENTORY {
        int inventory_id PK
        smallint film_id FK
        smallint store_id FK
        timestamp last_update
    }

    RENTAL {
        int rental_id PK
        timestamp rental_date
        int inventory_id FK
        smallint customer_id FK
        timestamp return_date
        smallint staff_id FK
        timestamp last_update
    }

    PAYMENT {
        int payment_id PK
        smallint customer_id FK
        smallint staff_id FK
        int rental_id FK
        numeric amount
        timestamp payment_date
    }

    %% People & Organization Entities
    CUSTOMER {
        int customer_id PK
        smallint store_id FK
        varchar first_name
        varchar last_name
        varchar email
        smallint address_id FK
        boolean activebool
        date create_date
        timestamp last_update
        int active
    }

    STAFF {
        int staff_id PK
        varchar first_name
        varchar last_name
        smallint address_id FK
        varchar email
        smallint store_id FK
        boolean active
        varchar username
        varchar password
        timestamp last_update
        bytea picture
    }

    STORE {
        int store_id PK
        smallint manager_staff_id FK
        smallint address_id FK
        timestamp last_update
    }

    ACTOR {
        int actor_id PK
        varchar first_name
        varchar last_name
        timestamp last_update
    }

    %% Content Classification Entities
    CATEGORY {
        int category_id PK
        varchar name
        timestamp last_update
    }

    LANGUAGE {
        int language_id PK
        char name
        timestamp last_update
    }

    %% Geographic Entities
    ADDRESS {
        int address_id PK
        varchar address
        varchar address2
        varchar district
        smallint city_id FK
        varchar postal_code
        varchar phone
        timestamp last_update
    }

    CITY {
        int city_id PK
        varchar city
        smallint country_id FK
        timestamp last_update
    }

    COUNTRY {
        int country_id PK
        varchar country
        timestamp last_update
    }

    %% Junction Tables
    FILM_ACTOR {
        smallint actor_id FK
        smallint film_id FK
        timestamp last_update
    }

    FILM_CATEGORY {
        smallint film_id FK
        smallint category_id FK
        timestamp last_update
    }

    %% View Tables (for analytics)
    ACTOR_INFO {
        int actor_id
        varchar first_name
        varchar last_name
        text film_info
    }

    CUSTOMER_LIST {
        int customer_id
        varchar name
        varchar address
        varchar phone
        varchar city
        varchar country
        varchar notes
        int sid
    }

    FILM_LIST {
        int fid
        varchar title
        varchar description
        varchar category
        numeric price
        smallint length
        varchar rating
        varchar actors
    }

    SALES_BY_FILM_CATEGORY {
        varchar category
        numeric total_sales
    }

    SALES_BY_STORE {
        varchar store
        varchar manager
        numeric total_sales
    }

    STAFF_LIST {
        int id
        varchar name
        varchar address
        varchar zip_code
        varchar phone
        varchar city
        varchar country
        int sid
    }

    %% Relationships
    FILM ||--o{ INVENTORY : "has"
    FILM ||--o{ FILM_ACTOR : "features"
    FILM ||--o{ FILM_CATEGORY : "belongs_to"
    FILM ||--o{ RENTAL : "rented_as"
    FILM }o--|| LANGUAGE : "in"

    INVENTORY ||--o{ RENTAL : "rented_in"
    INVENTORY }o--|| STORE : "located_at"

    RENTAL ||--o{ PAYMENT : "generates"
    RENTAL }o--|| CUSTOMER : "made_by"
    RENTAL }o--|| STAFF : "processed_by"

    CUSTOMER }o--|| STORE : "belongs_to"
    CUSTOMER }o--|| ADDRESS : "lives_at"

    STAFF }o--|| STORE : "works_at"
    STAFF }o--|| ADDRESS : "lives_at"

    STORE }o--|| STAFF : "managed_by"
    STORE }o--|| ADDRESS : "located_at"

    ACTOR ||--o{ FILM_ACTOR : "appears_in"
    CATEGORY ||--o{ FILM_CATEGORY : "classifies"

    ADDRESS }o--|| CITY : "in"
    CITY }o--|| COUNTRY : "in"

    %% Self-referencing relationships
    STAFF ||--o{ STORE : "manages"
```

## Key Features of the ER Diagram

### **Core Business Flow**
1. **Film → Inventory → Rental → Payment** - The main rental transaction flow
2. **Store → Staff → Customer** - Operational hierarchy
3. **Film ↔ Actor/Category** - Content classification system

### **Geographic Hierarchy**
- **Country → City → Address → Store/Customer/Staff** - Complete location tracking

### **Many-to-Many Relationships**
- **Film ↔ Actor** via `FILM_ACTOR` junction table
- **Film ↔ Category** via `FILM_CATEGORY` junction table

### **Analytical Views**
- **Business Intelligence**: Sales by category, sales by store
- **Customer Management**: Customer lists with full address information
- **Content Discovery**: Film lists with actor and category information
- **Staff Management**: Staff lists with store assignments

### **Data Integrity Features**
- **Foreign Key Constraints**: Maintains referential integrity
- **Audit Trails**: `last_update` timestamps on all tables
- **Status Tracking**: Active flags for customers and staff
- **Business Rules**: Default rental duration, pricing, and replacement costs

This ER diagram represents a comprehensive DVD rental management system designed for scalability, data integrity, and business intelligence.
