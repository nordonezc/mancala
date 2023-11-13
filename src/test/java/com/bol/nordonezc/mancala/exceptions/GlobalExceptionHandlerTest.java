package com.bol.nordonezc.mancala.exceptions;

import com.bol.nordonezc.mancala.dto.GameDto;
import com.bol.nordonezc.mancala.dto.GenericResponse;
import com.bol.nordonezc.mancala.utils.ErrorMessage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();


    @ParameterizedTest
    @MethodSource({"boardException"})
    void handleBoardException(BoardException boardException) {
        ResponseEntity<GenericResponse<GameDto>> response = globalExceptionHandler.handleException(boardException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(boardException.getErrorCode().name(), response.getBody().getError().get(0).getCode());
        assertEquals(boardException.getErrorCode().getMessage(), response.getBody().getError().get(0).getDescription());
    }

    private static Stream<BoardException> boardException() {
        return Stream.of(new BoardException(ErrorMessage.NO_BOARD_FOUND),
                new BoardException(ErrorMessage.EMPTY_POSITION)
        );
    }
}
