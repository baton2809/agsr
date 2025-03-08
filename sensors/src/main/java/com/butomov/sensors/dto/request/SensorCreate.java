package com.butomov.sensors.dto.request;

import com.butomov.sensors.dto.response.RangeDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorCreate {

    private String name;
    private String model;
    private RangeDto range;
    private String type;
    private String unit;
    private String location;
    private String description;
}
