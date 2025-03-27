CREATE TABLE IF NOT EXISTS api_keys (
        id SERIAL PRIMARY KEY,
        key_value VARCHAR(255) NOT NULL,
        request_count INT DEFAULT 0,
        last_reset TIMESTAMP NULL
    );

CREATE TABLE IF NOT EXISTS weather_data (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        city VARCHAR(255) NOT NULL,
        country VARCHAR(255) NOT NULL,
        description VARCHAR(255) NOT NULL
    );