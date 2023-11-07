package com.bol.nordonezc.mancala.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Response<T> {

    private final LocalDateTime responseTime;
    private final T body;
    private final String error;

    public Response(T body) {
        this(body, null);
    }

    public Response(String error) {
        this(null, error);
    }

    public Response(T body, String error) {
        this.responseTime = LocalDateTime.now();
        this.body = body;
        this.error = error;
    }
}
