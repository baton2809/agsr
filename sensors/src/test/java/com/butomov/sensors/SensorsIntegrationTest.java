package com.butomov.sensors;

import com.butomov.sensors.dto.request.SensorCreate;
import com.butomov.sensors.dto.response.RangeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SensorsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private SensorCreate sensorCreate;


    @BeforeEach
    void setUp() throws Exception {
        sensorCreate = SensorCreate.builder()
                .name("Temperature Sensor")
                .model("T100")
                .type("TEMPERATURE")
                .unit("CELSIUS")
                .range(new RangeDto(0, 100))
                .location("Room 1")
                .description("A sensor for monitoring temperature")
                .build();

        mockMvc.perform(post("/api/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sensorCreate)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void testCreateSensor() throws Exception {
        mockMvc.perform(post("/api/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sensorCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Temperature Sensor"))
                .andExpect(jsonPath("$.model").value("T100"));
    }

    @Test
    void testGetSensorById() throws Exception {
        mockMvc.perform(get("/api/sensors/" + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Temperature Sensor"));
    }

    @Test
    void testDeleteSensor() throws Exception {
        mockMvc.perform(delete("/api/sensors/" + 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetSensors() throws Exception {
        mockMvc.perform(get("/api/sensors")
                        .param("offset", "0")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
