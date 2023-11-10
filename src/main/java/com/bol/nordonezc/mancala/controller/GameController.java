package com.bol.nordonezc.mancala.controller;

import com.bol.nordonezc.mancala.dto.CreateGameRequestDto;
import com.bol.nordonezc.mancala.dto.GameDto;
import com.bol.nordonezc.mancala.dto.GenericResponse;
import com.bol.nordonezc.mancala.dto.PlayRequestDto;
import com.bol.nordonezc.mancala.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/game")
public class GameController {

    private final GameService gameService;

    @PostMapping("")
    public ResponseEntity<GenericResponse<GameDto>> createGame(@RequestBody(required = false)
                                                               @Valid
                                                               CreateGameRequestDto requestDto) {
        var gameInput = Optional.ofNullable(requestDto).orElse(new CreateGameRequestDto());
        return ResponseEntity.ok(new GenericResponse<>(gameService.createGame(gameInput.getStones())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<GameDto>> getGame(@PathVariable UUID id) {
        return ResponseEntity.ok(
                new GenericResponse<>(gameService.getGame(id)));
    }

    @PostMapping("/{id}")
    public ResponseEntity<GenericResponse<GameDto>> playGame(@PathVariable UUID id,
                                                             @RequestBody
                                                             @Valid
                                                             PlayRequestDto requestDto) {
        return ResponseEntity.ok(
                new GenericResponse<>(gameService.playGame(id, requestDto.getPosition())));
    }
}