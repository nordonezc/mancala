package com.bol.nordonezc.mancala.service;

import com.bol.nordonezc.mancala.domain.Board;

import java.util.UUID;

public interface GameService {

    UUID createGame(Integer stones);

    Board getGame(UUID boardId);

    Board playGame(UUID boardId, int position);

}