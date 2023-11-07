package com.bol.nordonezc.mancala.exceptions;

import com.bol.nordonezc.mancala.domain.Board;
import com.bol.nordonezc.mancala.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BoardException.class})
    public ResponseEntity<Response<Board>> handleBoardException(BoardException exception) {
        return ResponseEntity.badRequest().body(new Response<Board>(exception.getMessage()));
    }
}
