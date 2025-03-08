package com.butomov.sensors.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "sensor_type")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String name;
}

