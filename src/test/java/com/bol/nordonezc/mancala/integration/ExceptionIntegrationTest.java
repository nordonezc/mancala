package com.bol.nordonezc.mancala.integration;

import com.bol.nordonezc.mancala.dto.GenericResponse;
import com.bol.nordonezc.mancala.repository.BoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.util.Map;

import static com.bol.nordonezc.mancala.utils.ErrorMessage.INVALID_INPUT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {IntegrationTestConfig.class})
@AutoConfigureMockMvc
class ExceptionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BoardRepository boardRepository;

    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/resources/createGame_stonesBoolean.json",
            "src/test/resources/createGame_stonesMin.json",
            "src/test/resources/createGame_stonesString.json"})
    void testValidationExceptionHandling(String input) throws Exception {
        var inputObject = mapper.readValue(new File(input), Map.class);
        var result = mockMvc.perform(post("/v1/game")
                        .content(mapper.writeValueAsString(inputObject))
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