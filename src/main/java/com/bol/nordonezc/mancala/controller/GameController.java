package com.bol.nordonezc.mancala.controller;

import com.bol.nordonezc.mancala.domain.Board;
import com.bol.nordonezc.mancala.dto.Response;
import com.bol.nordonezc.mancala.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/game")
public class GameController {

    @Autowired
    private final GameService gameService;

    @PostMapping("")
    public ResponseEntity<Response<UUID>> createGame() {
        return ResponseEntity.ok(new Response<>(gameService.createGame()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Board>> createGame(@PathVariable UUID id) {
        return ResponseEntity.ok(new Response<>(gameService.getGame(id)));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Response<Board>> playGame(@PathVariable UUID id,
                                                    @RequestBody
                                                    Integer position) {
        return ResponseEntity.ok(new Response<>(gameService.playGame(id, position)));
    }
}
