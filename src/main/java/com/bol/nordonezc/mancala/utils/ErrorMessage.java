package com.bol.nordonezc.mancala.utils;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    EMPTY_POSITION("That position does not have any stones."),
    WINNER_SELECTED("The board already has a winner."),
    NO_BOARD_FOUND("The board id does not exists."),
    INVALID_INPUT("The given input has no proper values.");
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

}