package com.bol.nordonezc.mancala.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T extends Serializable> {

    private final LocalDateTime responseTime;
    private final T body;
    private final List<ErrorMessage> error;

    public Response(T body) {
        this(body, null);
    }

    public Response(List<ErrorMessage> error) {
        this(null, error);
    }

    public Response(T body, List<ErrorMessage> error) {
        this.responseTime = LocalDateTime.now();
        this.body = body;
        this.error = error;
    }
}