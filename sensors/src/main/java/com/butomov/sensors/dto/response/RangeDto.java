package com.butomov.sensors.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RangeDto {

    @JsonProperty("from")
    private Integer rangeFrom;

    @JsonProperty("to")
    private Integer rangeTo;
}
