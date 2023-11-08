package com.bol.nordonezc.mancala.mappers;

import com.bol.nordonezc.mancala.domain.Board;
import com.bol.nordonezc.mancala.dto.BoardDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class BoardMapper {

    public BoardMapper() {
        // This mapper does not need any additional mapper to work properly
    }

    public BoardDto map(Board board) {
        return BoardDto.builder()
                .firstPlayerPits(Arrays.copyOfRange(board.getPits(), 0, 6))
                .firstPlayerMancala(board.getPits()[6])
                .secondPlayerPits(Arrays.copyOfRange(board.getPits(), 7, 13))
                .secondPlayerMancala(board.getPits()[13])
                .winner(board.getWinner())
                .playerTurn(board.getPlayerTurn())
                .build();
    }
}