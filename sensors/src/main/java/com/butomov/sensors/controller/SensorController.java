package com.butomov.sensors.controller;

import com.butomov.sensors.dto.request.SensorCreate;
import com.butomov.sensors.dto.response.SensorDto;
import com.butomov.sensors.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @Operation(summary = "Get list of sensors", description = "Retrieve a paginated list of sensors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of sensors"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters provided")
    })
    @GetMapping
    public ResponseEntity<List<SensorDto>> getSensors(
            @RequestParam(defaultValue = "0") @Parameter(description = "offset") int offset,
            @RequestParam(defaultValue = "10") @Parameter(description = "limit") int limit) {

        List<SensorDto> sensors = sensorService.getSensors(offset, limit);
        if (sensors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sensors);
    }

    @Operation(summary = "Get sensor by ID", description = "Retrieve a sensor by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the sensor"),
            @ApiResponse(responseCode = "404", description = "Sensor not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SensorDto> getSensorsById(@PathVariable Long id) {
        SensorDto sensor = sensorService.getSensorById(id);
        if (sensor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sensor);
    }

    @Operation(summary = "Create a new sensor", description = "Create a new sensor and store it in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new sensor"),
            @ApiResponse(responseCode = "400", description = "Invalid sensor data provided")
    })
    @PostMapping
    public ResponseEntity<SensorDto> createSensor(@Valid @RequestBody @Parameter(description = "Sensor data to create") SensorCreate sensor) {
        SensorDto createdSensor = sensorService.createSensor(sensor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSensor);
    }

    @Operation(summary = "Update an existing sensor", description = "Update the details of an existing sensor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the sensor"),
            @ApiResponse(responseCode = "404", description = "Sensor not found"),
            @ApiResponse(responseCode = "400", description = "Invalid sensor data provided")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SensorDto> updateSensor(@PathVariable Long id, @Valid @RequestBody @Parameter(description = "Updated sensor data") SensorCreate sensor) {
        SensorDto updatedSensor = sensorService.updateSensor(id, sensor);
        if (updatedSensor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedSensor);
    }

    @Operation(summary = "Delete a sensor", description = "Delete an existing sensor by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the sensor"),
            @ApiResponse(responseCode = "404", description = "Sensor not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensor(@PathVariable Long id) {
        sensorService.deleteSensor(id);
        return ResponseEntity.noContent().build();
    }
}
