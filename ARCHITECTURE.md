```
┌─────────────────────────────────────────────────────────────────────────────┐
│                         E-COMMERCE ARCHITECTURE                             │
└─────────────────────────────────────────────────────────────────────────────┘

┌────────────────────────────────────┐
│       FRONTEND (React)              │
│  ├─ http://localhost:3000           │
│  ├─ Components                      │
│  ├─ Services                        │
│  └─ API Client (httpClient.js)      │
└────────────┬─────────────────────────┘
             │
             │ HTTP Requests (REST)
             │ Base URL: http://localhost:8081/api
             │
┌────────────▼─────────────────────────┐
│    BACKEND (Spring Boot Java)         │
│  ├─ http://localhost:8081            │
│  ├─ Port: 8081                       │
│  ├─ Swagger UI: /swagger-ui.html      │
│  │                                   │
│  ├─ Controllers                      │
│  │  ├─ AuthController                │
│  │  ├─ ProductController             │
│  │  ├─ CategoryController            │
│  │  └─ CustomerController            │
│  │                                   │
│  ├─ Services                         │
│  │  ├─ AuthService                   │
│  │  ├─ ProductService                │
│  │  ├─ CategoryService               │
│  │  └─ CustomerService               │
│  │                                   │
│  ├─ Repositories (JPA)               │
│  │  ├─ UserRepository                │
│  │  ├─ ProductRepository             │
│  │  ├─ CategoryRepository            │
│  │  └─ CustomerRepository            │
│  │                                   │
│  └─ Security                         │
│     ├─ Spring Security               │
│     └─ JWT Authentication            │
└────────────┬─────────────────────────┘
             │
             │ JDBC / Hibernate
             │
┌────────────▼──────────────────────────────────┐
│    DATABASE (PostgreSQL)                      │
│  ├─ Host: localhost:5432                     │
│  ├─ Database: ecommerce                      │
│  ├─ User: postgres                           │
│  ├─ Password: postgres                       │
│  │                                           │
│  └─ Tables (Auto-created by Hibernate):      │
│     ├─ users (UserEntity)                    │
│     ├─ products (ProductEntity)              │
│     ├─ categories (CategoryEntity)           │
│     └─ customers (CustomerEntity)            │
└───────────────────────────────────────────────┘


┌─────────────────────────────────────────────────────────────────────────────┐
│                        DATA FLOW EXAMPLE                                     │
└─────────────────────────────────────────────────────────────────────────────┘

1. User clicks "Add to Cart"
   │
   ├─→ React Frontend sends: POST /api/cart
   │   (body: {productId: 123, quantity: 2})
   │
   └─→ Backend receives request
       │
       ├─→ AuthController validates JWT token
       │
       ├─→ ProductController processes request
       │   │
       │   └─→ ProductService handles logic
       │       │
       │       └─→ ProductRepository.findById(123)
       │           │
       │           └─→ SELECT * FROM products WHERE id = 123
       │
       └─→ Response sent back to Frontend
           (status: 200, data: {...})


┌─────────────────────────────────────────────────────────────────────────────┐
│                    DATABASE SCHEMA                                           │
└─────────────────────────────────────────────────────────────────────────────┘

┌──────────────┐
│    USERS     │          ┌──────────────┐
├──────────────┤          │ CUSTOMERS    │
│ id (PK)      │          ├──────────────┤
│ username     │◄────┐    │ id (PK)      │
│ email        │     │    │ name         │
│ password     │     │    │ email        │
│ role         │     │    │ phone        │
└──────────────┘     │    │ address      │
                     │    └──────────────┘
                     │
                     └──── Foreign Key Relationship


┌──────────────┐         ┌──────────────┐
│ CATEGORIES   │◄────────│  PRODUCTS    │
├──────────────┤         ├──────────────┤
│ id (PK)      │         │ id (PK)      │
│ name         │────────→│ name         │
│ description  │ 1:N     │ sku          │
└──────────────┘         │ price        │
                         │ stock        │
                         │ is_active    │
                         │ category_id  │
                         │   (FK)       │
                         └──────────────┘


┌─────────────────────────────────────────────────────────────────────────────┐
│                      SECURITY FLOW                                           │
└─────────────────────────────────────────────────────────────────────────────┘

1. LOGIN
   Client: POST /api/auth/login
   {username: "user", password: "pass"}
   │
   └─→ Server validates credentials against users table
       └─→ Returns JWT token

2. AUTHENTICATED REQUEST
   Client: GET /api/products
   Header: Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
   │
   └─→ Server validates JWT token
       └─→ JwtAuthenticationFilter checks validity
           └─→ Allows request to proceed OR returns 401


┌─────────────────────────────────────────────────────────────────────────────┐
│                    STARTUP SEQUENCE                                          │
└─────────────────────────────────────────────────────────────────────────────┘

1. Execute: run-backend.bat (or run-backend.sh)
   │
2. Spring Boot Application starts
   │
3. Loads application.properties
   ├─ Port: 8081
   ├─ Database URL: jdbc:postgresql://localhost:5432/ecommerce
   ├─ User/Password: postgres/postgres
   └─ DDL Auto: update
   │
4. Hibernate initializes
   ├─ Connects to PostgreSQL
   ├─ Creates/Updates tables from Entities
   └─ Ready for queries
   │
5. Spring Security configured
   ├─ JWT provider initialized
   └─ Interceptors registered
   │
6. OpenAPI/Swagger initialized
   └─ Documentation available at /swagger-ui.html
   │
7. All Controllers registered
   └─ API ready for requests
   │
8. Print: "Backend de E-Commerce iniciado correctamente."

✅ Server is running!
✅ Access: http://localhost:8081
✅ Swagger: http://localhost:8081/swagger-ui.html
```
