package com.bol.nordonezc.mancala.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class GenericResponse<T extends Serializable> extends RepresentationModel<GenericResponse<T>> implements Serializable {

    private LocalDateTime responseTime;
    private T body;
    private List<ErrorMessage> error;

    public GenericResponse(T body) {
        this(body, null);
    }

    public GenericResponse(List<ErrorMessage> error) {
        this(null, error);
    }

    public GenericResponse(T body, List<ErrorMessage> error) {
        this.responseTime = LocalDateTime.now();
        this.body = body;
        this.error = error;
    }

}