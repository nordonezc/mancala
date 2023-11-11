package com.bol.nordonezc.mancala.integration;

import com.bol.nordonezc.mancala.dto.GenericResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {IntegrationTestConfig.class})
@AutoConfigureMockMvc
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Container
    private static final RedisContainer REDIS_CONTAINER =
            new RedisContainer(DockerImageName.parse("redis")).withExposedPorts(6379);

    @Test
    void testCreateGame() throws Exception {
        var inputObject = mapper.readValue(new File("src/test/resources/createGame_stonesValid.json"), Map.class);
        var result = mockMvc.perform(post("/v1/game")
                        .content(mapper.writeValueAsString(inputObject))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var successResponse = mapper.readValue(result.getResponse().getContentAsString(), GenericResponse.class);
        assertNotNull(successResponse.getBody());
    }

}