# Phone Store - Spring Boot Application

A RESTful API application built with Spring Boot for managing a phone store inventory.

## Features

- **CRUD Operations**: Create, Read, Update, and Delete phone records
- **Search Functionality**: Search phones by brand, model, or price range
- **Validation**: Input validation for all API endpoints
- **Exception Handling**: Global exception handling with meaningful error messages
- **H2 Database**: In-memory database for easy setup and testing
- **RESTful API**: Standard REST endpoints with proper HTTP status codes

## Technologies Used

- **Spring Boot 3.2.1**
- **Java 17**
- **Spring Data JPA**
- **H2 Database**
- **Lombok**
- **Maven**

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/banghuu11/Phone_Store.git
cd Phone_Store
```

### 2. Build the project

```bash
mvn clean install
```

### 3. Run the application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Phone Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/phones` | Get all phones |
| GET | `/api/phones/{id}` | Get phone by ID |
| POST | `/api/phones` | Create a new phone |
| PUT | `/api/phones/{id}` | Update a phone |
| DELETE | `/api/phones/{id}` | Delete a phone |

### Search Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/phones/search/brand?brand={brand}` | Search by brand |
| GET | `/api/phones/search/model?model={model}` | Search by model |
| GET | `/api/phones/search/price?minPrice={min}&maxPrice={max}` | Search by price range |

## Sample Request

### Create a Phone

```bash
curl -X POST http://localhost:8080/api/phones \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Apple",
    "model": "iPhone 15 Pro",
    "price": 999.99,
    "description": "Latest iPhone with A17 Pro chip",
    "stockQuantity": 50,
    "imageUrl": "https://example.com/iphone15pro.jpg"
  }'
```

### Get All Phones

```bash
curl http://localhost:8080/api/phones
```

### Search by Brand

```bash
curl http://localhost:8080/api/phones/search/brand?brand=Apple
```

## Database

The application uses an H2 in-memory database. You can access the H2 console at:

```
http://localhost:8080/h2-console
```

**Connection Details:**
- JDBC URL: `jdbc:h2:mem:phonestoredb`
- Username: `sa`
- Password: (leave empty)

## Project Structure

```
src/
├── main/
│   ├── java/com/phonestore/app/
│   │   ├── controller/      # REST controllers
│   │   ├── model/           # Entity classes
│   │   ├── repository/      # JPA repositories
│   │   ├── service/         # Business logic
│   │   ├── exception/       # Exception handling
│   │   └── PhoneStoreApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/phonestore/app/
```

## Testing

Run the tests using:

```bash
mvn test
```

## License

This project is open source and available under the MIT License.