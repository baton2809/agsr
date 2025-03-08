//package com.butomov.sensors.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(SensorNotFoundException.class)
//    public ResponseEntity<String> handleSensorNotFound(SensorNotFoundException e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(ValidationException.class)
//    public ResponseEntity<String> handleValidationException(ValidationException e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleGenericException(Exception e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}
