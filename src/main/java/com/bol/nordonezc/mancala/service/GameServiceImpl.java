package com.bol.nordonezc.mancala.service;

import com.bol.nordonezc.mancala.business.MancalaBoard;
import com.bol.nordonezc.mancala.exceptions.BoardException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.bol.nordonezc.mancala.utils.ErrorMessage.EMPTY_POSITION;
import static com.bol.nordonezc.mancala.utils.ErrorMessage.NO_BOARD_FOUND;
import static com.bol.nordonezc.mancala.utils.ErrorMessage.WINNER_SELECTED;
import static com.bol.nordonezc.mancala.utils.PitUtils.EMPTY_PIT;
import static com.bol.nordonezc.mancala.utils.PitUtils.FIRST_PLAYER;
import static com.bol.nordonezc.mancala.utils.PitUtils.NO_PLAYER;


@Service
public class GameServiceImpl implements GameService {

    private final Map<UUID, MancalaBoard> match;

    public GameServiceImpl() {
        this.match = new ConcurrentHashMap<>();
    }

    @Override
    public UUID createGame(int stones) {
        UUID newBoardID = UUID.randomUUID();
        this.match.put(newBoardID, new MancalaBoard(stones));
        return newBoardID;
    }

    @Override
    public MancalaBoard getGame(UUID boardId) {
        var boardToPlay = this.match.get(boardId);

        if (boardToPlay == null) {
            throw new BoardException(NO_BOARD_FOUND);
        }

        return boardToPlay;
    }

    @Override
    public MancalaBoard playGame(UUID boardId, int position) {
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

        boardToPlay.playTurn(positionToPlay);
        return boardToPlay;
    }
}