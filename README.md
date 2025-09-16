# FlightsightBack

FlightsightBack is the backend for the solution Flightsight.

# Architecture

## Data Modeling (JPA/Hibernate)

I created **four entities** and their **associated repositories**, which simplify database queries.

### Review Entity
- The **`Review`** entity includes a **`ManyToOne`** relationship with **`Flight`**, linked via **`flightId`**.
- This design optimizes searches for reviews by airline. (Currently implemented)

## Controllers and Services

I created **two controllers**: one for flights and another for reviews. The main goals were:
- Loading flight data from open sources.
- Adding reviews linked to flights.

### Flight Controller
This controller includes:
- **Two standard endpoints**:
  - Fetch a flight by its ID.
  - Search flights with **pagination** and **sorting**.
- **One development-only endpoint**:
  - Retrieves data from **OpenSky** and loads it into the database in the correct format.
  - **Next step**: Automate this process to import data one or multiple times a day.
  - **Potential improvement**: Data quality, especially for departure and arrival dates.

### Review Controller
This controller implements a **SCRUD** (Search-Create-Read-Update-Delete) via **five endpoints**, including:
- Search with **pagination**, **sorting**, and **filters** on review data and flight data (airline).

**Documentation**: All endpoints are documented for **Swagger**.

### Associated Services
Each controller relies on a **dedicated service** that handles business logic and interacts with **repositories** to access data.

---
## Security

The goal was to use **Spring Security** to:
- **Authenticate** users.
- **Control access** to the API.

**Current status**: All requests are open for development purposes.

### OpenSky API Access

For development, the OpenSky API token is **currently hardcoded** in the project.

**Next step**: Move credentials to a **secure secrets manager**.

# Flightsight Database

This database can manage flights, customers, their reviews, and associated responses.

I selected PostgreSQL because I have prior experience using it in other projects and am already familiar with relational databases and SQL. 

PostgreSQL offers well-documented opportunities for performance optimization and scalability, backed by a large and active open-source community. 

Additionally, in our current project, we use Flyway for database migrations, and it's compatible with PostgreSQL.

---

## Tables and Relationships

### 1. `flights`
Stores flight information.
   | Field                | Type         | Description                   |
   | -------------------- | ------------ | ----------------------------- |
   | `flight_id`          | BIGSERIAL    | Unique flight identifier (PK) |
   | `flight_number`      | VARCHAR(20)  | Flight number                 |
   | `airline`            | VARCHAR(100) | Airline name                  |
   | `departure_datetime` | TIMESTAMP    | Departure date and time       |
   | `arrival_datetime`   | TIMESTAMP    | Arrival date and time         |
   | `departure_airport`  | VARCHAR(100) | Departure airport             |
   | `arrival_airport`    | VARCHAR(100) | Arrival airport               |
   | `created_at`         | TIMESTAMP    | Record creation timestamp     |

**Indexes:**
- `idx_flights_flight_number`: Index on flight number.
- `idx_flights_airline`: Index on airline.
- `idx_flights_departure_airport`: Index on departure airport.
- `idx_flights_arrival_airport`: Index on arrival airport.
- `idx_flights_departure_datetime`: Index on departure datetime.

---

### 2. `customers`
Stores customer information.
 | Field         | Type         | Description                     |
 | ------------- | ------------ | ------------------------------- |
 | `customer_id` | BIGSERIAL    | Unique customer identifier (PK) |
 | `last_name`   | VARCHAR(100) | Last name                       |
 | `first_name`  | VARCHAR(100) | First name                      |
 | `email`       | VARCHAR(100) | Email address (unique)          |
 | `created_at`  | TIMESTAMP    | Record creation timestamp       |

**Indexes:**
- `idx_customers_email`: Index on email.
- `idx_customers_name`: Index on last name and first name.

---

### 3. `reviews`
Stores customer reviews for flights.
 | Field         | Type        | Description                                                         |
 | ------------- | ----------- | ------------------------------------------------------------------- |
 | `review_id`   | BIGSERIAL   | Unique review identifier (PK)                                       |
 | `customer_id` | BIGINT      | Customer identifier (FK)                                            |
 | `flight_id`   | BIGINT      | Flight identifier (FK)                                              |
 | `rating`      | INT         | Rating (1 to 10)                                                    |
 | `title`       | TEXT        | Review title                                                        |
 | `comment`     | TEXT        | Review comment                                                      |
 | `created_at`  | TIMESTAMP   | Review creation timestamp                                           |
 | `updated_at`  | TIMESTAMP   | Review update timestamp                                             |
 | `status`      | VARCHAR(20) | Review status (`UNPROCESSED`, `PROCESSED`, `PUBLISHED`, `REJECTED`) |

**Indexes:**
- `idx_reviews_rating`: Index on rating.
- `idx_reviews_create_at`: Index on creation timestamp.
- `idx_reviews_status`: Index on status.
- `idx_reviews_customer_id`: Index on customer identifier.
- `idx_reviews_flight_id`: Index on flight identifier.

**Constraints:**
- `FOREIGN KEY (customer_id)`: References `customers(customer_id)`.
- `FOREIGN KEY (flight_id)`: References `flights(flight_id)`.

---

### 4. `responses`
Stores responses to reviews.
 | Field           | Type      | Description                     |
 | --------------- | --------- | ------------------------------- |
 | `response_id`   | BIGSERIAL | Unique response identifier (PK) |
 | `review_id`     | BIGINT    | Review identifier (FK)          |
 | `response_text` | TEXT      | Response text                   |
 | `response_date` | TIMESTAMP | Response date                   |

**Indexes:**
- `idx_responses_review_id`: Index on review identifier.
- `idx_responses_response_date`: Index on response date.

**Constraints:**
- `FOREIGN KEY (review_id)`: References `reviews(review_id)`.

---

## Relationship Schema
- A **flight** can have multiple **reviews**.
- A **customer** can leave multiple **reviews**.
- A **review** can have one **response**.

## Development server

To start a local development server, run:

```bash
mvn clean install -DskipTests
java -jar target/flightsight-back-0.0.1-SNAPSHOT.jar
```

Once the server is running, open your browser and navigate to `http://localhost:8080/swagger-ui/index.html` to see all the endpoints provided.

## Building

To build the project run:

```bash
mvn clean install
```

This will compile your project and store the build jar in the `target/` directory.

## AI Assistance in Development

For this Spring Boot project, I used **AI tools** to:

## AI Assistance in Spring Boot Development

For this Spring Boot project, I utilized AI tools to:
- **Generate JPA entities**
- **Help with SQL syntax** 
- **Configure project setup**
- **Troubleshoot issues**
- **Understand framework mechanics**