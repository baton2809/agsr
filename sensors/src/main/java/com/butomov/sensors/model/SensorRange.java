package com.butomov.sensors.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "sensor_range")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorRange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Integer rangeFrom;

    @NotNull
    @Column(nullable = false)
    private Integer rangeTo;
}