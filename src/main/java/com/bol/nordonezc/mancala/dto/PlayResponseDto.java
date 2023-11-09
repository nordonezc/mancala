package com.bol.nordonezc.mancala.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PlayResponseDto implements Serializable {

    private UUID id;
    private int[] firstPlayerPits;
    private int firstPlayerMancala;
    private int[] secondPlayerPits;
    private int secondPlayerMancala;
    private int winner;
    private int playerTurn;

}