package com.bol.nordonezc.mancala.dto;

import lombok.Data;

import java.io.Serializable;

import static com.bol.nordonezc.mancala.utils.PitUtils.DEFAULT_STONE_AMOUNT;

@Data
public class CreateGameRequestDto implements Serializable {

    private Integer stones;

    public CreateGameRequestDto() {
        this.stones = DEFAULT_STONE_AMOUNT;
    }
}