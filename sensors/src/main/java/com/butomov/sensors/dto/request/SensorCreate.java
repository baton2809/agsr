package com.butomov.sensors.dto.request;

import com.butomov.sensors.dto.response.RangeDto;
import com.butomov.sensors.validation.ValidRange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorCreate {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

    @NotBlank
    @Size(max = 15)
    private String model;

    @ValidRange
    private RangeDto range;

    @NotBlank
    private String type;

    private String unit;

    @Size(max = 40)
    private String location;

    @Size(max = 200)
    private String description;
}
