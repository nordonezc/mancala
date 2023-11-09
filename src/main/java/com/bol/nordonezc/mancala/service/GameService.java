package com.bol.nordonezc.mancala.service;

import com.bol.nordonezc.mancala.business.MancalaBoard;

import java.util.UUID;

public interface GameService {

    UUID createGame(int stones);

    MancalaBoard getGame(UUID boardId);

    MancalaBoard playGame(UUID boardId, int position);

}