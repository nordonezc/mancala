package com.bol.nordonezc.mancala.service;

import com.bol.nordonezc.mancala.dto.GameDto;

import java.util.UUID;

public interface GameService {

    GameDto createGame(int stones);

    GameDto getGame(UUID boardId);

    GameDto playGame(UUID boardId, int position);

}