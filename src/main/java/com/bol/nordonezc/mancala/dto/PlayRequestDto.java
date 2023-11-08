package com.bol.nordonezc.mancala.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class PlayRequestDto implements Serializable {

    @NotNull
    @Min(value = 1)
    @Max(value = 6)
    private Integer position;
}