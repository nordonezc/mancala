package com.bol.nordonezc.mancala.exceptions;

import com.bol.nordonezc.mancala.config.IntegrationTestConfig;
import com.bol.nordonezc.mancala.dto.GenericResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static com.bol.nordonezc.mancala.utils.ErrorMessage.INVALID_INPUT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {IntegrationTestConfig.class})
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/resources/createGame_stonesBoolean.json",
            "src/test/resources/createGame_stonesString.json"})
    void testValidationExceptionHandling(String input) throws Exception {
        File inputJson = new File(input);
        var result = mockMvc.perform(post("/v1/game")
                        .content(mapper.writeValueAsString(inputJson))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", hasSize(1)))
                .andExpect(status().isBadRequest())
                .andReturn();

        var error = mapper.readValue(result.getResponse().getContentAsString(), GenericResponse.class);
        assertThat(error,
                hasProperty("error", hasItem(
                        allOf(hasProperty("code", containsString(INVALID_INPUT.name()))
                        ))));

    }

}