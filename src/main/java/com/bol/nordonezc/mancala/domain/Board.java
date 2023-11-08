package com.bol.nordonezc.mancala.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

import static com.bol.nordonezc.mancala.utils.PitUtils.*;

@Getter
@Setter
public class Board {

    private Integer[] pits;
    private Integer winner;
    private Integer playerTurn;

    public Board(Integer stones) {
        pits = new Integer[14];
        Arrays.fill(pits, stones);
        pits[FIRST_PLAYER_BIG_PIT] = EMPTY_PIT;
        pits[SECOND_PLAYER_BIG_PIT] = EMPTY_PIT;
        this.winner = NO_PLAYER;
        this.playerTurn = FIRST_PLAYER;
    }
}