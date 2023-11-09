package com.bol.nordonezc.mancala.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayRequestDto implements Serializable {

    @NotNull
    @Min(value = 1)
    @Max(value = 6)
    private Integer position;
}