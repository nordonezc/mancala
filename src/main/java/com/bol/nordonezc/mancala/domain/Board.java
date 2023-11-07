package com.bol.nordonezc.mancala.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

import static com.bol.nordonezc.mancala.utils.PitUtils.*;

@Getter
@Setter
public class Board {

    private final int[] pits;
    private int winner;
    private int playerTurn;

    public Board() {
        pits = new int[14];
        Arrays.fill(pits, INITIAL_STONES_PER_PIT);
        pits[FIRST_PLAYER_BIG_PIT] = EMPTY_PIT;
        pits[SECOND_PLAYER_BIG_PIT] = EMPTY_PIT;
        this.winner = NO_PLAYER;
        this.playerTurn = FIRST_PLAYER;
    }
}