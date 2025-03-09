package com.butomov.sensors.controller;

import com.butomov.sensors.dto.request.SensorCreate;
import com.butomov.sensors.dto.response.SensorDto;
import com.butomov.sensors.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensors")
@Validated
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @Operation(summary = "Get list of sensors", description = "Retrieve a paginated list of sensors")
    @PreAuthorize("hasAnyRole('ADMIN', 'VIEWER')")
    @GetMapping
    public ResponseEntity<List<SensorDto>> getSensors(
            @RequestParam(defaultValue = "0") @Parameter(description = "offset") int offset,
            @RequestParam(defaultValue = "10") @Parameter(description = "limit") int limit) {
        List<SensorDto> sensors = sensorService.getSensors(offset, limit);
        return sensors.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(sensors);
    }

    @Operation(summary = "Search a sensor", description = "Search for a sensor by substring")
    @PreAuthorize("hasAnyRole('ADMIN', 'VIEWER')")
    @GetMapping("/search")
    public List<SensorDto> searchSensors(@RequestParam String query) {
        return sensorService.searchSensors(query);
    }

    @Operation(summary = "Get sensor by ID", description = "Retrieve a sensor by its unique ID")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<SensorDto> getSensorById(@PathVariable Long id) {
        SensorDto sensor = sensorService.getSensorById(id);
        return ResponseEntity.ok(sensor);
    }

    @Operation(summary = "Create a new sensor", description = "Create a new sensor and store it in the database")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SensorDto> createSensor(@Valid @RequestBody @Parameter(description = "Sensor data to create") SensorCreate sensor) {
        SensorDto createdSensor = sensorService.createSensor(sensor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSensor);
    }

    @Operation(summary = "Update an existing sensor", description = "Update the details of an existing sensor")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SensorDto> updateSensor(@PathVariable Long id, @Valid @RequestBody @Parameter(description = "Updated sensor data") SensorCreate sensor) {
        SensorDto updatedSensor = sensorService.updateSensor(id, sensor);
        return ResponseEntity.ok(updatedSensor);
    }

    @Operation(summary = "Delete a sensor", description = "Delete an existing sensor by its unique ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensor(@PathVariable Long id) {
        sensorService.deleteSensor(id);
        return ResponseEntity.noContent().build();
    }
}
