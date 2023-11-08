package com.bol.nordonezc.mancala.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {

    private String code;
    private String description;

}