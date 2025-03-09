package com.butomov.sensors.exception;

public class SensorNotFoundException extends RuntimeException {

    public SensorNotFoundException(Long id) {
        super("Sensor not found with id: " + id);
    }
}
