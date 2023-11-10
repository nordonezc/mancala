package com.bol.nordonezc.mancala.controller;

import com.bol.nordonezc.mancala.mappers.BoardMapper;
import com.bol.nordonezc.mancala.repository.BoardRepository;
import com.bol.nordonezc.mancala.service.GameService;
import com.bol.nordonezc.mancala.service.GameServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;

class GameControllerTestConfig {

    @Bean
    GameController gameController(GameService gameService) {
        return new GameController(gameService);
    }

    @Bean
    BoardRepository boardRepository() {
        return Mockito.mock(BoardRepository.class);
    }

    @Bean
    BoardMapper getBoardMapper() {
        return new BoardMapper();
    }

    @Bean
    GameService gameService(BoardRepository boardRepository, BoardMapper boardMapper) {
        return new GameServiceImpl(boardRepository, boardMapper);
    }


}