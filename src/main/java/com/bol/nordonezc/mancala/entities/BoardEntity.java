package com.bol.nordonezc.mancala.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@RedisHash("Board")
@Data
@AllArgsConstructor
public class BoardEntity implements Serializable {

    @Id
    private String id;
    @Indexed
    private int winner;
    private int playerTurn;
    private int[] pits;

}
