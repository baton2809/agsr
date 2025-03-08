CREATE TABLE sensor_type (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO sensor_type (name) VALUES
    ('PRESSURE'),
    ('VOLTAGE'),
    ('TEMPERATURE'),
    ('HUMIDITY');

CREATE TABLE sensor_unit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO sensor_unit (name) VALUES
    ('BAR'),
    ('VOLTAGE'),
    ('CELSIUS'),
    ('PERCENT');

CREATE TABLE sensor_range (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    range_from INT NOT NULL CHECK (range_from >= 0 AND range_from <= 100),
    range_to INT NOT NULL CHECK (range_to >= 0 AND range_to <= 100)
);

CREATE TABLE sensor (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    model VARCHAR(15) NOT NULL,
    range_id BIGINT NOT NULL,
    type_id BIGINT NOT NULL REFERENCES sensor_type(id),
    unit_id BIGINT NOT NULL REFERENCES sensor_unit(id),
    location VARCHAR(40),
    description VARCHAR(200),
    FOREIGN KEY (range_id) REFERENCES sensor_range(id)
);