package com.bol.nordonezc.mancala.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;

import static com.bol.nordonezc.mancala.utils.PitUtils.EMPTY_PIT;
import static com.bol.nordonezc.mancala.utils.PitUtils.FIRST_PLAYER;
import static com.bol.nordonezc.mancala.utils.PitUtils.FIRST_PLAYER_BIG_PIT;
import static com.bol.nordonezc.mancala.utils.PitUtils.NO_PLAYER;
import static com.bol.nordonezc.mancala.utils.PitUtils.NUMBER_OF_PLAYERS;
import static com.bol.nordonezc.mancala.utils.PitUtils.PITS_AMOUNT_PER_PLAYER;
import static com.bol.nordonezc.mancala.utils.PitUtils.SECOND_PLAYER;
import static com.bol.nordonezc.mancala.utils.PitUtils.SECOND_PLAYER_BIG_PIT;
import static com.bol.nordonezc.mancala.utils.PitUtils.cleanBoardRemains;
import static com.bol.nordonezc.mancala.utils.PitUtils.getNextPlayerTurn;
import static com.bol.nordonezc.mancala.utils.PitUtils.isFirstPlayerPits;
import static com.bol.nordonezc.mancala.utils.PitUtils.isOneSideOutOfStones;
import static com.bol.nordonezc.mancala.utils.PitUtils.isSecondPlayerPits;
import static com.bol.nordonezc.mancala.utils.PitUtils.validPitPositionToFill;

@Getter
@Builder
@AllArgsConstructor
public class MancalaBoard {

    private final int[] pits;
    private int winner;
    private int playerTurn;

    public MancalaBoard(int stones) {
        pits = new int[PITS_AMOUNT_PER_PLAYER * NUMBER_OF_PLAYERS + NUMBER_OF_PLAYERS];
        Arrays.fill(pits, 0, FIRST_PLAYER_BIG_PIT, stones);
        Arrays.fill(pits, FIRST_PLAYER_BIG_PIT + 1, SECOND_PLAYER_BIG_PIT, stones);
        this.winner = NO_PLAYER;
        this.playerTurn = FIRST_PLAYER;
    }

    public void playTurn(int position) {
        int lastPositionSowed = sowStonesFrom(position);
        playerTurn = getNextPlayerTurn(playerTurn, lastPositionSowed);

        if (isOneSideOutOfStones(pits)) {
            cleanBoardRemains(pits);
            winner = pits[FIRST_PLAYER_BIG_PIT] > pits[SECOND_PLAYER_BIG_PIT] ?
                    FIRST_PLAYER : SECOND_PLAYER;
        }
    }

    /**
     * Go to the position given, sow all the stones, validate if it is possible
     * to capture enemy pit, and return the last sowed position
     *
     * @param position Position to start the sowing
     * @return Last position sowed
     */
    private int sowStonesFrom(int position) {
        int pitPosition = sowStones(position);

        if (pits[pitPosition] == 1) {
            captureEnemyPit(pitPosition);
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
    private int sowStones(int position) {
        int stonesToMove = pits[position];
        pits[position] = EMPTY_PIT;
        int pitPosition = position;
        for (int stonesAllocated = 0; stonesAllocated < stonesToMove; stonesAllocated++) {
            pitPosition++;
            pitPosition = pitPosition >= pits.length ? 0 :
                    validPitPositionToFill(playerTurn, pitPosition);
            pits[pitPosition]++;
        }
        return pitPosition;
    }

    /**
     * Get all stones from the enemy pit
     *
     * @param pitPosition Reference of the position to check enemy pit
     */
    private void captureEnemyPit(int pitPosition) {
        int enemyPit = (pits.length - NUMBER_OF_PLAYERS) - (pitPosition);
        if (playerTurn == FIRST_PLAYER && isFirstPlayerPits(pitPosition)) {
            pits[FIRST_PLAYER_BIG_PIT] += pits[enemyPit] + 1;
            pits[enemyPit] = EMPTY_PIT;
            pits[pitPosition] = EMPTY_PIT;
        }
        if (playerTurn == SECOND_PLAYER && isSecondPlayerPits(pitPosition)) {
            pits[SECOND_PLAYER_BIG_PIT] += pits[enemyPit] + 1;
            pits[enemyPit] = EMPTY_PIT;
            pits[pitPosition] = EMPTY_PIT;
        }
    }
}