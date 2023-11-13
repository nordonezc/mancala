package com.bol.nordonezc.mancala.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
public class Trace {

    private LocalDateTime creationDate;
    @Indexed
    private LocalDateTime modificationDate;
}
