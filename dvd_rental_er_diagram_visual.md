# DVD Rental Database - Visual ER Diagram

## Visual Entity Relationship Diagram

```mermaid
graph TB
    %% Core Business Entities
    subgraph "Film Management"
        FILM[FILM<br/>film_id, title, description<br/>rental_rate, replacement_cost]
        ACTOR[ACTOR<br/>actor_id, first_name, last_name]
        CATEGORY[CATEGORY<br/>category_id, name]
        LANGUAGE[LANGUAGE<br/>language_id, name]
        FILM_ACTOR[FILM_ACTOR<br/>actor_id, film_id]
        FILM_CATEGORY[FILM_CATEGORY<br/>film_id, category_id]
    end

    subgraph "Store Operations"
        STORE[STORE<br/>store_id, manager_staff_id]
        STAFF[STAFF<br/>staff_id, first_name, last_name<br/>username, password]
        INVENTORY[INVENTORY<br/>inventory_id, film_id, store_id]
    end

    subgraph "Customer & Rental"
        CUSTOMER[CUSTOMER<br/>customer_id, first_name, last_name<br/>email, store_id]
        RENTAL[RENTAL<br/>rental_id, rental_date<br/>return_date, customer_id]
        PAYMENT[PAYMENT<br/>payment_id, amount<br/>customer_id, rental_id]
    end

    subgraph "Address Management"
        ADDRESS[ADDRESS<br/>address_id, address, district<br/>postal_code, phone]
        CITY[CITY<br/>city_id, city, country_id]
        COUNTRY[COUNTRY<br/>country_id, country]
    end

    %% Relationships
    FILM -->|"1:N"| INVENTORY
    FILM -->|"M:N"| FILM_ACTOR
    FILM -->|"M:N"| FILM_CATEGORY
    FILM -->|"N:1"| LANGUAGE
    
    ACTOR -->|"M:N"| FILM_ACTOR
    CATEGORY -->|"M:N"| FILM_CATEGORY
    
    STORE -->|"1:N"| INVENTORY
    STORE -->|"1:N"| CUSTOMER
    STORE -->|"1:N"| STAFF
    STAFF -->|"1:1"| STORE
    
    INVENTORY -->|"1:N"| RENTAL
    CUSTOMER -->|"1:N"| RENTAL
    STAFF -->|"1:N"| RENTAL
    RENTAL -->|"1:1"| PAYMENT
    
    CUSTOMER -->|"N:1"| ADDRESS
    STAFF -->|"N:1"| ADDRESS
    STORE -->|"N:1"| ADDRESS
    ADDRESS -->|"N:1"| CITY
    CITY -->|"N:1"| COUNTRY

    %% Styling
    classDef entity fill:#e1f5fe,stroke:#01579b,stroke-width:2px
    classDef junction fill:#f3e5f5,stroke:#4a148c,stroke-width:2px
    classDef relationship fill:#e8f5e8,stroke:#1b5e20,stroke-width:1px

    class FILM,ACTOR,CATEGORY,LANGUAGE,STORE,STAFF,INVENTORY,CUSTOMER,RENTAL,PAYMENT,ADDRESS,CITY,COUNTRY entity
    class FILM_ACTOR,FILM_CATEGORY junction
```

## Simplified ER Diagram (Core Tables Only)

```mermaid
erDiagram
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
    STAFF ||--o{ RENTAL : "processes"
    RENTAL ||--|| PAYMENT : "generates"
    
    CUSTOMER ||--o{ PAYMENT : "pays"
    STAFF ||--o{ PAYMENT : "receives"
    
    CUSTOMER }o--|| ADDRESS : "lives_at"
    STAFF }o--|| ADDRESS : "lives_at"
    STORE }o--|| ADDRESS : "located_at"
    ADDRESS }o--|| CITY : "in"
    CITY }o--|| COUNTRY : "in"
```

## Database Schema Overview

### **Core Business Entities**

| Entity | Purpose | Key Attributes |
|--------|---------|----------------|
| **FILM** | Movie catalog | title, description, rental_rate, rating |
| **ACTOR** | Cast information | first_name, last_name |
| **CATEGORY** | Film genres | name |
| **LANGUAGE** | Available languages | name |
| **STORE** | Physical locations | manager_staff_id |
| **STAFF** | Store employees | username, password, store_id |
| **CUSTOMER** | Rental customers | first_name, last_name, email, store_id |
| **INVENTORY** | Physical copies | film_id, store_id |
| **RENTAL** | Rental transactions | rental_date, return_date |
| **PAYMENT** | Financial records | amount, payment_date |

### **Key Relationships**

1. **Film Management**
   - Films can have multiple actors (M:N via FILM_ACTOR)
   - Films can belong to multiple categories (M:N via FILM_CATEGORY)
   - Each film has one language

2. **Store Operations**
   - Each store has one manager (staff member)
   - Stores employ multiple staff members
   - Stores serve multiple customers
   - Stores stock multiple inventory items

3. **Rental Process**
   - Customers make multiple rentals
   - Staff process multiple rentals
   - Each rental generates one payment
   - Inventory items are rented multiple times

4. **Address Hierarchy**
   - Country → City → Address → Customer/Staff/Store
   - Geographic data is normalized for consistency

### **Business Rules & Constraints**

- **Referential Integrity**: All foreign keys maintain data consistency
- **Audit Trail**: Timestamp tracking on all major entities
- **Store Association**: Customers and staff are tied to specific stores
- **Inventory Management**: Physical copies tracked per store location
- **Payment Processing**: One-to-one relationship between rentals and payments
