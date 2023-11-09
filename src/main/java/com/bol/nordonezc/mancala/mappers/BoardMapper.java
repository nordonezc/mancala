package com.bol.nordonezc.mancala.mappers;

import com.bol.nordonezc.mancala.business.MancalaBoard;
import com.bol.nordonezc.mancala.dto.PlayResponseDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.bol.nordonezc.mancala.utils.PitUtils.FIRST_PLAYER_BIG_PIT;
import static com.bol.nordonezc.mancala.utils.PitUtils.SECOND_PLAYER_BIG_PIT;

@Component
public class BoardMapper {

    public BoardMapper() {
        // This mapper does not need any additional mapper to work properly
    }

    public PlayResponseDto map(MancalaBoard mancalaBoard) {
        return PlayResponseDto.builder()
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
}