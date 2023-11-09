package com.bol.nordonezc.mancala.exceptions;

import com.bol.nordonezc.mancala.utils.ErrorMessage;
import lombok.Getter;

@Getter
public class BoardException extends RuntimeException {

    private final ErrorMessage errorCode;

    public BoardException(ErrorMessage errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}