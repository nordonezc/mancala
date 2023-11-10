package com.bol.nordonezc.mancala.controller;

import com.bol.nordonezc.mancala.business.MancalaBoard;
import com.bol.nordonezc.mancala.dto.CreateGameRequestDto;
import com.bol.nordonezc.mancala.dto.PlayRequestDto;
import com.bol.nordonezc.mancala.entities.BoardEntity;
import com.bol.nordonezc.mancala.mappers.BoardMapper;
import com.bol.nordonezc.mancala.repository.BoardRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.bol.nordonezc.mancala.utils.PitUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GameControllerTestConfig.class})
class GameControllerTest {

    @Autowired
    private GameController gameController;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeAll
    void beforeAll() {
    }

    @Test
    void createGame_whenStones2_shouldBoardWithSpecificNumberOfStones() {
        var stonesPerPit = 2;
        MancalaBoard mancalaBoard = new MancalaBoard(stonesPerPit);
        BoardEntity boardEntity = boardMapper.mapBoardToEntity(UUID.randomUUID(), mancalaBoard);
        when(boardRepository.save(any())).thenReturn(boardEntity);

        var response = gameController.createGame(new CreateGameRequestDto(stonesPerPit));
        var boardCreated = Objects.requireNonNull(response.getBody()).getBody();

        assertEquals(EMPTY_PIT, boardCreated.getFirstPlayerMancala());
        assertEquals(EMPTY_PIT, boardCreated.getSecondPlayerMancala());
        assertTrue(Arrays.stream(boardCreated.getFirstPlayerPits()).allMatch(value -> value == stonesPerPit));
        assertTrue(Arrays.stream(boardCreated.getSecondPlayerPits()).allMatch(value -> value == stonesPerPit));


    }

    @Test
    void createGame_whenNoStonesAreGiven_shouldDefaultBoard() {
        MancalaBoard mancalaBoard = new MancalaBoard(DEFAULT_STONE_AMOUNT);
        BoardEntity boardEntity = boardMapper.mapBoardToEntity(UUID.randomUUID(), mancalaBoard);
        when(boardRepository.save(any())).thenReturn(boardEntity);

        var response = gameController.createGame(null);
        var actualBody = Objects.requireNonNull(response.getBody()).getBody();

        assertEquals(boardMapper.mapEntityToDto(boardEntity), actualBody);
    }

    @Test
    void getGame_whenExistentUUID_shouldOkBoard() {
        MancalaBoard mancalaBoard = new MancalaBoard(DEFAULT_STONE_AMOUNT);
        BoardEntity boardEntity = boardMapper.mapBoardToEntity(UUID.randomUUID(), mancalaBoard);
        when(boardRepository.findById(any())).thenReturn(Optional.of(boardEntity));
        var existentBoard = boardMapper.mapEntityToDto(boardEntity);

        var response = gameController.getGame(existentBoard.getId());
        var actualBody = Objects.requireNonNull(response.getBody()).getBody();

        assertEquals(existentBoard, actualBody);
    }

    @Test
    void playGame_whenExistentUUID_shouldOkBoard() {
        MancalaBoard mancalaBoard = new MancalaBoard(DEFAULT_STONE_AMOUNT);
        BoardEntity boardEntity = boardMapper.mapBoardToEntity(UUID.randomUUID(), mancalaBoard);
        when(boardRepository.findById(any())).thenReturn(Optional.of(boardEntity));
        var existentBoard = boardMapper.mapEntityToDto(boardEntity);

        var request = new PlayRequestDto(5);
        var response = gameController.playGame(existentBoard.getId(), request);
        var actualBody = Objects.requireNonNull(response.getBody()).getBody();

        assertEquals(1, actualBody.getFirstPlayerMancala());
        assertEquals(EMPTY_PIT, actualBody.getSecondPlayerMancala());
        assertEquals(NO_PLAYER, actualBody.getWinner());
        assertEquals(SECOND_PLAYER, actualBody.getPlayerTurn());
    }

}