package com.bol.nordonezc.mancala.service;

import com.bol.nordonezc.mancala.domain.Board;
import com.bol.nordonezc.mancala.exceptions.BoardException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.bol.nordonezc.mancala.utils.MessageConstants.*;
import static com.bol.nordonezc.mancala.utils.PitUtils.*;


@Service
public class GameServiceImpl implements GameService {

    Map<UUID, Board> match;

    public GameServiceImpl() {
        this.match = new HashMap<>();
    }

    @Override
    public UUID createGame() {
        UUID newBoardID = UUID.randomUUID();
        this.match.put(newBoardID, new Board());
        return newBoardID;
    }

    @Override
    public Board getGame(UUID boardId) {
        var boardToPlay = this.match.get(boardId);

        if (boardToPlay == null) {
            throw new BoardException(NO_BOARD_FOUND);
        }

        return boardToPlay;
    }

    @Override
    public Board playGame(UUID boardId, int position) {
        var boardToPlay = this.getGame(boardId);
        int positionToPlay = boardToPlay.getPlayerTurn() == FIRST_PLAYER ?
                position - 1 :
                position - 1 + 7;

        if (boardToPlay.getWinner() != NO_PLAYER) {
            throw new BoardException(WINNER_SELECTED);
        }
        if (boardToPlay.getPits()[positionToPlay] == EMPTY_PIT) {
            throw new BoardException(EMPTY_POSITION);
        }

        return playTurn(boardToPlay, positionToPlay);
    }


    private Board playTurn(Board board, Integer position) {
        int lastPositionSowed = sowStonesFrom(board, position);
        board.setPlayerTurn(getNextPlayerTurn(board.getPlayerTurn(), lastPositionSowed));

        if (isOneSideOutOfStones(board.getPits())) {
            board.setWinner(board.getPits()[FIRST_PLAYER_BIG_PIT] > board.getPits()[SECOND_PLAYER_BIG_PIT] ? 1 : 2);
        }

        return board;
    }

    /**
     * Go to the position given, sow all the stones, validate if it is possible
     * to capture enemy pit, and return the last sowed position
     *
     * @param position Position to start the sowing
     * @return Last position sowed
     */
    private int sowStonesFrom(Board board, Integer position) {
        int pitPosition = sowStones(board, position);

        if (board.getPits()[pitPosition] == 1) {
            captureEnemyPit(board, pitPosition);
        }
        return pitPosition;
    }


    /**
     * Get all stones from the position requested and
     * sow them into the next pits clockwise.
     *
     * @param position Pit to empty
     * @return Position of the last stone allocated
     */
    private int sowStones(Board board, Integer position) {
        int stonesToMove = board.getPits()[position];
        board.getPits()[position] = EMPTY_PIT;
        int pitPosition = position;
        for (int stonesAllocated = 0; stonesAllocated < stonesToMove; stonesAllocated++) {
            pitPosition++;
            pitPosition = pitPosition >= board.getPits().length ? 0 :
                    validPitPositionToFill(board.getPlayerTurn(), pitPosition);
            board.getPits()[pitPosition]++;
        }
        return pitPosition;
    }

    /**
     * Get all stones from the enemy pit
     *
     * @param pitPosition Reference of the position to check enemy pit
     */
    private void captureEnemyPit(Board board, int pitPosition) {
        int enemyPit = board.getPits().length - pitPosition - 1;
        if (board.getPlayerTurn() == FIRST_PLAYER && isFirstPlayerPits(pitPosition)) {
            board.getPits()[FIRST_PLAYER_BIG_PIT] += board.getPits()[enemyPit] + 1;
            board.getPits()[enemyPit] = EMPTY_PIT;
            board.getPits()[pitPosition] = EMPTY_PIT;
        }
        if (board.getPlayerTurn() == SECOND_PLAYER && isSecondPlayerPits(pitPosition)) {
            board.getPits()[SECOND_PLAYER_BIG_PIT] += board.getPits()[enemyPit] + 1;
            board.getPits()[enemyPit] = EMPTY_PIT;
            board.getPits()[pitPosition] = EMPTY_PIT;
        }
    }
}
