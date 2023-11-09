package com.bol.nordonezc.mancala.exceptions;

import com.bol.nordonezc.mancala.dto.PlayResponseDto;
import com.bol.nordonezc.mancala.dto.ErrorMessage;
import com.bol.nordonezc.mancala.dto.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

import static com.bol.nordonezc.mancala.utils.ErrorMessage.INVALID_INPUT;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<GenericResponse<PlayResponseDto>> handleBoardException(Exception exception) {
        var errorMessage = ErrorMessage.builder()
                .code(INVALID_INPUT.name())
                .description(exception.getMessage()).build();
        return ResponseEntity.badRequest().body(new GenericResponse<>(List.of(errorMessage)));
    }

    @ExceptionHandler
    public ResponseEntity<GenericResponse<PlayResponseDto>> handleBoardException(BoardException exception) {
        var errorMessage = ErrorMessage.builder()
                .code(exception.getErrorCode().name())
                .description(exception.getMessage()).build();
        return ResponseEntity.badRequest().body(new GenericResponse<>(List.of(errorMessage)));
    }

    @ExceptionHandler
    public ResponseEntity<GenericResponse<PlayResponseDto>> handleBoardException(MethodArgumentNotValidException exception) {
        List<ErrorMessage> response = exception.getBindingResult().getAllErrors().stream()
                .map(error -> ErrorMessage.builder()
                        .code(INVALID_INPUT.name())
                        .description(((FieldError) error).getField() + ":" + error.getDefaultMessage()).build())
                .toList();

        return ResponseEntity.badRequest().body(new GenericResponse<>(response));
    }
}