package com.butomov.sensors.service;

import com.butomov.sensors.dto.request.SensorCreate;
import com.butomov.sensors.dto.response.SensorDto;
import com.butomov.sensors.dto.response.RangeDto;
import com.butomov.sensors.mapper.SensorMapper;
import com.butomov.sensors.model.Sensor;
import com.butomov.sensors.model.SensorRange;
import com.butomov.sensors.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return mapSensorsToDTO(sensorRepository.findAll(pageable).toList());
    }

    public SensorDto getSensorById(Long id) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sensor not found"));
        return mapSensorToDTO(sensor);
    }

    public SensorDto createSensor(SensorCreate sensor) {
        Sensor createdSensor = sensorRepository.save(mapper.toEntity(sensor));
        return mapSensorToDTO(createdSensor);
    }

    public SensorDto updateSensor(Long id, SensorCreate sensor) {
        Sensor toUpdate = mapper.toEntity(sensor);
        toUpdate.setId(id);
        Sensor updatedSensor = sensorRepository.save(toUpdate);
        return mapSensorToDTO(updatedSensor);
    }

    public void deleteSensor(Long id) {
        sensorRepository.deleteById(id);
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
