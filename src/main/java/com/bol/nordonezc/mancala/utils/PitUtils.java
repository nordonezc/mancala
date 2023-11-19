package com.bol.nordonezc.mancala.utils;

public class PitUtils {


    public static final int FIRST_PLAYER_BIG_PIT = 6;
    public static final int SECOND_PLAYER_BIG_PIT = 13;
    public static final int EMPTY_PIT = 0;
    public static final int DEFAULT_STONE_AMOUNT = 6;
    public static final int PITS_AMOUNT_PER_PLAYER = 6;
    public static final int NO_PLAYER = 0;
    public static final int FIRST_PLAYER = 1;
    public static final int SECOND_PLAYER = 2;
    public static final int NUMBER_OF_PLAYERS = 2;

    private PitUtils() {
    }

    /**
     * If it is first player playing, skip to fill second player's pot.
     * If it is second player playing, skip to fill first player's pot.
     * Otherwhise, keep filling the position given
     *
     * @param pitPosition Position to fill
     * @return Next pit position to fill
     */
    public static int validPitPositionToFill(int playerTurn, int pitPosition) {
        if (playerTurn == FIRST_PLAYER && pitPosition == SECOND_PLAYER_BIG_PIT) {
            return 0;
        }

        if (playerTurn == SECOND_PLAYER && pitPosition == FIRST_PLAYER_BIG_PIT) {
            return FIRST_PLAYER_BIG_PIT + 1;
        }
        return pitPosition;
    }

    /**
     * Validate if the actual position owns to first player
     *
     * @param pitPosition Actual pit position to validate ownership
     * @return true if is first player pits, otherwise false
     */
    public static boolean isFirstPlayerPits(int pitPosition) {
        return pitPosition < FIRST_PLAYER_BIG_PIT;
    }


    /**
     * Validate if the actual position owns to second player
     *
     * @param pitPosition Actual pit position to validate ownership
     * @return true if is second player pits, otherwise false
     */
    public static boolean isSecondPlayerPits(int pitPosition) {
        return pitPosition > FIRST_PLAYER_BIG_PIT && pitPosition < SECOND_PLAYER_BIG_PIT;
    }

    /**
     * Determine what is the next player to play. If the last position sowed
     * does not end in the big pit of the player who plays, its the other player
     * turn
     *
     * @param playerTurn        Actual player turn
     * @param lastPositionSowed Last position sowed in the last turn
     * @return next player turn
     */
    public static int getNextPlayerTurn(int playerTurn, int lastPositionSowed) {
        if (playerTurn == FIRST_PLAYER && lastPositionSowed != FIRST_PLAYER_BIG_PIT) {
            return SECOND_PLAYER;
        }
        if (playerTurn == SECOND_PLAYER && lastPositionSowed != SECOND_PLAYER_BIG_PIT) {
            return FIRST_PLAYER;
        }
        return playerTurn;
    }


    /**
     * Check if the range of pits for each player is empty
     *
     * @param pits Actual board
     * @return true if any of the sides are empty
     */
    public static boolean isOneSideOutOfStones(int[] pits) {
        return checkPitRangeEmptiness(pits, 0, FIRST_PLAYER_BIG_PIT) ||
                checkPitRangeEmptiness(pits, FIRST_PLAYER_BIG_PIT + 1, SECOND_PLAYER_BIG_PIT);
    }

    /**
     * Clean stones from both sides and sum up to each mancala
     *
     * @param pits Actual board
     */
    public static void cleanBoardRemains(int[] pits) {
        var remainingStones = cleanAllPits(pits, 0, FIRST_PLAYER_BIG_PIT);
        pits[FIRST_PLAYER_BIG_PIT] += remainingStones;
        remainingStones = cleanAllPits(pits, FIRST_PLAYER_BIG_PIT + 1, SECOND_PLAYER_BIG_PIT);
        pits[SECOND_PLAYER_BIG_PIT] += remainingStones;
    }

    /**
     * Check within a range if all positions are with no stones
     *
     * @param pits          Board with all the stones
     * @param startingPoint Start position to check emptiness
     * @param endingPoint   Last position to check emptiness
     * @return true if all the positions within the range are empty, otherwise false
     */
    public static boolean checkPitRangeEmptiness(int[] pits, int startingPoint, int endingPoint) {
        for (int i = startingPoint; i < endingPoint; i++) {
            if (pits[i] != 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Take all the stones from the pits positions range given
     *
     * @param pits          Pits to clean
     * @param startingPoint Initial range of the pits
     * @param endingPoint   End range of the pits
     * @return Sum of all the stones in the pits range
     */
    public static int cleanAllPits(int[] pits, int startingPoint, int endingPoint) {
        int sum = 0;
        for (int i = startingPoint; i < endingPoint; i++) {
            sum += pits[i];
            pits[i] = 0;
        }
        return sum;
    }
}