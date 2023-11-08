package com.bol.nordonezc.mancala.utils;

import lombok.Getter;

@Getter
public enum MessageConstants {

    EMPTY_POSITION("That position does not have any stones."),
    WINNER_SELECTED("The board already has a winner."),
    NO_BOARD_FOUND("The board id does not exists.");
    private final String message;

    MessageConstants(String message) {
        this.message = message;
    }

}