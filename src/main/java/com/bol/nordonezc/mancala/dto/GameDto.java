package com.bol.nordonezc.mancala.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GameDto implements Serializable {

    private UUID id;
    private int[] firstPlayerPits;
    private int firstPlayerMancala;
    private int[] secondPlayerPits;
    private int secondPlayerMancala;
    private int winner;
    private int playerTurn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameDto gameDto = (GameDto) o;
        return firstPlayerMancala == gameDto.firstPlayerMancala && secondPlayerMancala == gameDto.secondPlayerMancala && winner == gameDto.winner && playerTurn == gameDto.playerTurn && Objects.equals(id, gameDto.id) && Arrays.equals(firstPlayerPits, gameDto.firstPlayerPits) && Arrays.equals(secondPlayerPits, gameDto.secondPlayerPits);
    }
}