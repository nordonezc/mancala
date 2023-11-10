package com.bol.nordonezc.mancala.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serializable;

import static com.bol.nordonezc.mancala.utils.PitUtils.DEFAULT_STONE_AMOUNT;

@Data
public class CreateGameRequestDto implements Serializable {

    @Min(1)
    private int stones;

    public CreateGameRequestDto() {
        this.stones = DEFAULT_STONE_AMOUNT;
    }

    public CreateGameRequestDto(int stones) {
        this.stones = stones;
    }
}