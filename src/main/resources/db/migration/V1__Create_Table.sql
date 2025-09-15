
-- Table flights
CREATE TABLE flights (
    flight_id BIGSERIAL PRIMARY KEY,
    flight_number VARCHAR(20) NOT NULL,
    airline VARCHAR(100) NOT NULL,
    departure_datetime TIMESTAMP NOT NULL,
    arrival_datetime TIMESTAMP NOT NULL,
    departure_airport VARCHAR(100) NOT NULL,
    arrival_airport VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Index for table flights
CREATE INDEX idx_flights_flight_number ON flights(flight_number);
CREATE INDEX idx_flights_airline ON flights(airline);
CREATE INDEX idx_flights_departure_airport ON flights(departure_airport);
CREATE INDEX idx_flights_arrival_airport ON flights(arrival_airport);
CREATE INDEX idx_flights_departure_datetime ON flights(departure_datetime);


-- Table customers
CREATE TABLE customers (
    customer_id BIGSERIAL PRIMARY KEY,
    last_name VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Index for table customers
CREATE INDEX idx_customers_email ON customers(email);
CREATE INDEX idx_customers_name ON customers(last_name, first_name);

-- Table reviews
CREATE TABLE reviews (
    review_id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    flight_id BIGINT NOT NULL,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 10),
    title TEXT NOT NULL,
    comment TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'UNPROCESSED' CHECK (status IN ('UNPROCESSED', 'PROCESSED', 'PUBLISHED', 'REJECTED')),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (flight_id) REFERENCES flights(flight_id)
);

-- Index for table reviews
CREATE INDEX idx_reviews_rating ON reviews(rating);
CREATE INDEX idx_reviews_create_at ON reviews(created_at);
CREATE INDEX idx_reviews_status ON reviews(status);
CREATE INDEX idx_reviews_customer_id ON reviews(customer_id);
CREATE INDEX idx_reviews_flight_id ON reviews(flight_id);

-- Table responses
CREATE TABLE responses (
    response_id BIGSERIAL PRIMARY KEY,
    review_id BIGINT NOT NULL,
    response_text TEXT NOT NULL,
    response_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (review_id) REFERENCES reviews(review_id)
);

-- Index for table responses
CREATE INDEX idx_responses_review_id ON responses(review_id);
CREATE INDEX idx_responses_response_date ON responses(response_date);
