package com.butomov.sensors.service;

import com.butomov.sensors.dto.request.SensorCreate;
import com.butomov.sensors.dto.response.RangeDto;
import com.butomov.sensors.dto.response.SensorDto;
import com.butomov.sensors.exception.SensorNotFoundException;
import com.butomov.sensors.exception.ValidationException;
import com.butomov.sensors.mapper.SensorMapper;
import com.butomov.sensors.model.Sensor;
import com.butomov.sensors.model.SensorRange;
import com.butomov.sensors.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private SensorMapper mapper;


    public List<SensorDto> getSensors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return sensorRepository.findAll(pageable)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public SensorDto getSensorById(Long id) {
        return sensorRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new SensorNotFoundException(id));
    }

    public SensorDto createSensor(SensorCreate sensor) {
        try {
            Sensor createdSensor = sensorRepository.save(mapper.toEntity(sensor));
            return mapper.toDto(createdSensor);
        } catch (DataAccessException e) {
            throw new ValidationException("Error saving sensor to database: " + e.getMessage());
        }
    }

    public SensorDto updateSensor(Long id, SensorCreate sensor) {
        Sensor existingSensor = sensorRepository.findById(id)
                .orElseThrow(() -> new SensorNotFoundException(id));
        try {
            Sensor toUpdate = mapper.toEntity(sensor);
            toUpdate.setId(existingSensor.getId());
            Sensor updatedSensor = sensorRepository.save(toUpdate);
            return mapper.toDto(updatedSensor);
        } catch (DataAccessException e) {
            throw new ValidationException("Error updating sensor: " + e.getMessage());
        }
    }

    public void deleteSensor(Long id) {
        if (!sensorRepository.existsById(id)) {
            throw new SensorNotFoundException(id);
        }
        try {
            sensorRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new ValidationException("Error deleting sensor: " + e.getMessage());
        }
    }

    public List<SensorDto> searchSensors(String query) {
        List<Sensor> sensors = sensorRepository.findByNameContainingIgnoreCaseOrModelContainingIgnoreCase(query, query);
        return sensors.stream().map(this::mapSensorToDTO).collect(Collectors.toList());
    }

    private SensorDto mapSensorToDTO(Sensor sensor) {
        return SensorDto.builder()
                .id(sensor.getId())
                .name(sensor.getName())
                .model(sensor.getModel())
                .range(mapRangeToDto(sensor.getRange()))
                .type(sensor.getType().getName())
                .unit(sensor.getUnit().getName())
                .location(sensor.getLocation())
                .description(sensor.getDescription())
                .build();
    }

    private List<SensorDto> mapSensorsToDTO(List<Sensor> sensors) {
        return sensors.stream()
                .map(this::mapSensorToDTO)
                .collect(Collectors.toList());
    }

    private RangeDto mapRangeToDto(SensorRange range) {
        return RangeDto.builder()
                .rangeFrom(range.getRangeFrom())
                .rangeTo(range.getRangeTo())
                .build();
    }
}
