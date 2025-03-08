package com.butomov.sensors.mapper;

import com.butomov.sensors.dto.request.SensorCreate;
import com.butomov.sensors.dto.response.SensorDto;
import com.butomov.sensors.model.Sensor;
import com.butomov.sensors.model.SensorRange;
import com.butomov.sensors.model.Type;
import com.butomov.sensors.model.Unit;
import com.butomov.sensors.dto.response.RangeDto;
import com.butomov.sensors.repository.TypeRepository;
import com.butomov.sensors.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorMapper {

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private UnitRepository unitRepository;

    public Sensor toEntity(SensorCreate sensorCreate) {
        Sensor sensor = new Sensor();
        sensor.setName(sensorCreate.getName());
        sensor.setModel(sensorCreate.getModel());
        sensor.setLocation(sensorCreate.getLocation());
        sensor.setDescription(sensorCreate.getDescription());

        SensorRange range = SensorRange.builder().rangeFrom(sensorCreate.getRange().getRangeFrom()).rangeTo(sensorCreate.getRange().getRangeTo()).build();
        sensor.setRange(range);

        Type type = typeRepository.findByName(sensorCreate.getType()).orElseThrow();
        sensor.setType(type);

        if (sensorCreate.getUnit() != null) {
            Unit unit = unitRepository.findByName(sensorCreate.getUnit()).orElseThrow();
            sensor.setUnit(unit);
        }

        return sensor;
    }

    public SensorDto toDto(SensorCreate sensor) {
        RangeDto rangeDto = new RangeDto(sensor.getRange().getRangeFrom(), sensor.getRange().getRangeTo());

        return SensorDto.builder()
                .name(sensor.getName())
                .model(sensor.getModel())
                .range(rangeDto)
                .type(sensor.getType())
                .unit(sensor.getUnit())
                .location(sensor.getLocation())
                .description(sensor.getDescription())
                .build();
    }
}
