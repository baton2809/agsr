package com.butomov.sensors.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorDto {

    private Long id;

    private String name;
    private String model;

    @JsonProperty("range")
    private RangeDto range;

    private String type;
    private String unit;
    private String location;
    private String description;
}
