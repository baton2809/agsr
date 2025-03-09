package com.butomov.sensors.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "sensor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 30)
    @Column(nullable = false, length = 30)
    private String name;

    @NotBlank
    @Size(max = 15)
    @Column(nullable = false, length = 15)
    private String model;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "range_id", nullable = false)
    private SensorRange range;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @Size(max = 40)
    @Column(length = 40)
    private String location;

    @Size(max = 200)
    @Column(length = 200)
    private String description;
}
