package com.bol.nordonezc.mancala.mappers;

import com.bol.nordonezc.mancala.business.MancalaBoard;
import com.bol.nordonezc.mancala.dto.GameDto;
import com.bol.nordonezc.mancala.entities.BoardEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

import static com.bol.nordonezc.mancala.utils.PitUtils.FIRST_PLAYER_BIG_PIT;
import static com.bol.nordonezc.mancala.utils.PitUtils.SECOND_PLAYER_BIG_PIT;

@Component
public class BoardMapper {

    public BoardMapper() {
        // This mapper does not need any additional mapper to work properly
    }

    public GameDto mapEntityToDto(BoardEntity mancalaBoard) {
        return GameDto.builder()
                .id(UUID.fromString(mancalaBoard.getId()))
                .firstPlayerPits(Arrays.copyOfRange(mancalaBoard.getPits(),
                        0,
                        FIRST_PLAYER_BIG_PIT))
                .firstPlayerMancala(mancalaBoard.getPits()[FIRST_PLAYER_BIG_PIT])
                .secondPlayerPits(Arrays.copyOfRange(mancalaBoard.getPits(),
                        FIRST_PLAYER_BIG_PIT + 1,
                        SECOND_PLAYER_BIG_PIT))
                .secondPlayerMancala(mancalaBoard.getPits()[SECOND_PLAYER_BIG_PIT])
                .winner(mancalaBoard.getWinner())
                .playerTurn(mancalaBoard.getPlayerTurn())
                .build();
    }

    public BoardEntity mapBoardToEntity(UUID id, MancalaBoard mancalaBoard) {
        return new BoardEntity(id.toString(),
                mancalaBoard.getWinner(),
                mancalaBoard.getPlayerTurn(),
                mancalaBoard.getPits());
    }

    public MancalaBoard mapEntityToBoard(BoardEntity boardEntity) {
        return MancalaBoard.builder()
                .pits(boardEntity.getPits())
                .playerTurn(boardEntity.getPlayerTurn())
                .winner(boardEntity.getWinner())
                .build();
    }
}