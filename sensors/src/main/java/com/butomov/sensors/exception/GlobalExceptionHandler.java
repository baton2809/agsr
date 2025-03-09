package com.butomov.sensors.exception;

import com.butomov.sensors.dto.response.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorDto>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<ErrorDto> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> ErrorDto.builder().code(fieldError.getField()).message(fieldError.getDefaultMessage()).status(HttpStatus.NOT_ACCEPTABLE.value()).build())
                .collect(Collectors.toList());

        return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(SensorNotFoundException.class)
    public ResponseEntity<ErrorDto> handleSensorNotFound(SensorNotFoundException e) {
        ErrorDto errorResponse = new ErrorDto(
                "SENSOR_NOT_FOUND",
                e.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDto> handleValidationException(ValidationException e) {
        ErrorDto errorResponse = new ErrorDto(
                "VALIDATION_ERROR",
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGenericException(Exception e) {
        ErrorDto errorResponse = new ErrorDto(
                "INTERNAL_SERVER_ERROR",
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
