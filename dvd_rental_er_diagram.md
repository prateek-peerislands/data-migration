# DVD Rental Database - Entity Relationship Diagram

## ER Diagram

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

    ACTOR {
        int actor_id PK
        varchar first_name
        varchar last_name
        timestamp last_update
    }

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

    %% Address Management
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

    %% Film Classification
    LANGUAGE {
        int language_id PK
        char name
        timestamp last_update
    }

    CATEGORY {
        int category_id PK
        varchar name
        timestamp last_update
    }

    %% Junction Tables
    FILM_ACTOR {
        smallint actor_id PK,FK
        smallint film_id PK,FK
        timestamp last_update
    }

    FILM_CATEGORY {
        smallint film_id PK,FK
        smallint category_id PK,FK
        timestamp last_update
    }

    %% View Tables (for reference)
    ACTOR_INFO {
        int actor_id
        varchar first_name
        varchar last_name
        text film_info
    }

    CUSTOMER_LIST {
        int id
        text name
        varchar address
        varchar zip_code
        varchar phone
        varchar city
        varchar country
        text notes
        smallint sid
    }

    FILM_LIST {
        int fid
        varchar title
        text description
        varchar category
        numeric price
        smallint length
        mpaa_rating rating
        text actors
    }

    SALES_BY_FILM_CATEGORY {
        varchar category
        numeric total_sales
    }

    SALES_BY_STORE {
        text store
        text manager
        numeric total_sales
    }

    STAFF_LIST {
        int id
        text name
        varchar address
        varchar zip_code
        varchar phone
        varchar city
        varchar country
        smallint sid
    }

    %% Relationships
    FILM ||--o{ INVENTORY : "has"
    FILM ||--o{ FILM_ACTOR : "features"
    FILM ||--o{ FILM_CATEGORY : "belongs_to"
    FILM }o--|| LANGUAGE : "in"
    
    ACTOR ||--o{ FILM_ACTOR : "appears_in"
    
    CATEGORY ||--o{ FILM_CATEGORY : "contains"
    
    STORE ||--o{ INVENTORY : "stocks"
    STORE ||--o{ CUSTOMER : "serves"
    STORE ||--o{ STAFF : "employs"
    STORE }o--|| STAFF : "managed_by"
    
    INVENTORY ||--o{ RENTAL : "rented_as"
    
    CUSTOMER ||--o{ RENTAL : "makes"
    CUSTOMER ||--o{ PAYMENT : "pays"
    CUSTOMER }o--|| ADDRESS : "lives_at"
    CUSTOMER }o--|| STORE : "registered_at"
    
    STAFF ||--o{ RENTAL : "processes"
    STAFF ||--o{ PAYMENT : "receives"
    STAFF }o--|| ADDRESS : "lives_at"
    STAFF }o--|| STORE : "works_at"
    
    RENTAL ||--|| PAYMENT : "generates"
    
    ADDRESS }o--|| CITY : "in"
    CITY }o--|| COUNTRY : "in"
```

## Database Schema Summary

### **Core Business Flow**
1. **Film Management**: Films are categorized and feature actors
2. **Inventory**: Physical copies are distributed across stores
3. **Customer Operations**: Customers rent films from stores
4. **Rental Process**: Staff process rentals and payments
5. **Geographic Structure**: Addresses organized by city/country hierarchy

### **Key Relationships**
- **One-to-Many**: Store → Staff, Store → Customer, Store → Inventory
- **Many-to-Many**: Film ↔ Actor, Film ↔ Category
- **One-to-One**: Rental ↔ Payment
- **Hierarchical**: Country → City → Address

### **Business Rules**
- Each film can have multiple actors and categories
- Each store has one manager (staff member)
- Customers are associated with one store
- Rentals generate exactly one payment
- Inventory items are unique per store
- All entities maintain audit timestamps

### **View Tables Purpose**
- **Reporting Views**: Sales by category/store, customer/staff lists
- **Business Intelligence**: Aggregated data for management decisions
- **User Experience**: Simplified data access for common queries
