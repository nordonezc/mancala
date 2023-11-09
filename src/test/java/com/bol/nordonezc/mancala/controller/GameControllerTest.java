package com.bol.nordonezc.mancala.controller;

import com.bol.nordonezc.mancala.config.IntegrationTestConfig;
import com.bol.nordonezc.mancala.dto.CreateGameRequestDto;
import com.bol.nordonezc.mancala.dto.PlayRequestDto;
import com.bol.nordonezc.mancala.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static com.bol.nordonezc.mancala.utils.PitUtils.DEFAULT_STONE_AMOUNT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = {IntegrationTestConfig.class})
class GameControllerTest {

    @Autowired
    GameController gameController;

    @Autowired
    GameService gameService;

    UUID boardCreatedID;

    @BeforeEach
    void setUp() {
        boardCreatedID = gameService.createGame(DEFAULT_STONE_AMOUNT);
    }

    @Test
    void createGame_whenStones2_shouldBoardWith2stones() {
        var stonesPerPit = 2;
        var response = gameController.createGame(new CreateGameRequestDto(stonesPerPit));
        var givenUUID = response.getBody().getBody();

        assertEquals(stonesPerPit, gameService.getGame(givenUUID).getPits()[0]);
        assertNotNull(givenUUID);
    }

    @Test
    void createGame_whenNoStonesAreGiven_shouldDefaultBoard() {
        var response = gameController.createGame(null);
        var givenUUID = response.getBody().getBody();
        assertNotNull(givenUUID);
    }

    @Test
    void getGame_whenExistentUUID_shouldOkBoard() {
        var response = gameController.getGame(boardCreatedID);
        var givenBoard = response.getBody().getBody();
        assertNotNull(givenBoard);
    }

    @Test
    void playGame_whenExistentUUID_shouldOkBoard() {
        var request = new PlayRequestDto(5);
        var response = gameController.playGame(boardCreatedID, request);
        var givenBoard = response.getBody().getBody();
        assertNotNull(givenBoard);
    }

}