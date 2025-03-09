package com.butomov.sensors.dto.response;

import com.butomov.sensors.validation.ValidRange;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidRange
public class RangeDto {

    @JsonProperty("from")
    private Integer rangeFrom;

    @JsonProperty("to")
    private Integer rangeTo;
}
