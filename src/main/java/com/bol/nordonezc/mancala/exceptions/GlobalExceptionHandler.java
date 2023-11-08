package com.bol.nordonezc.mancala.exceptions;

import com.bol.nordonezc.mancala.dto.BoardDto;
import com.bol.nordonezc.mancala.dto.ErrorMessage;
import com.bol.nordonezc.mancala.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.bol.nordonezc.mancala.utils.ErrorCode.INVALID_GAME;
import static com.bol.nordonezc.mancala.utils.ErrorCode.INVALID_INPUT;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {
            HttpMessageNotReadableException.class,
            BoardException.class,
            HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<Response<BoardDto>> handleBoardException(Exception exception) {
        var errorMessage = ErrorMessage.builder()
                .code(INVALID_GAME.name())
                .description(exception.getMessage()).build();
        return ResponseEntity.badRequest().body(new Response<>(List.of(errorMessage)));
    }

    @ExceptionHandler
    public ResponseEntity<Response<BoardDto>> handleBoardException(MethodArgumentNotValidException exception) {
        List<ErrorMessage> response = exception.getBindingResult().getAllErrors().stream()
                .map(error -> ErrorMessage.builder()
                        .code(INVALID_INPUT.name())
                        .description(((FieldError) error).getField() + ":" + error.getDefaultMessage()).build())
                .toList();

        return ResponseEntity.badRequest().body(new Response<>(response));
    }
}