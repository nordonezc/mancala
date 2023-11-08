package com.bol.nordonezc.mancala.controller;

import com.bol.nordonezc.mancala.dto.BoardDto;
import com.bol.nordonezc.mancala.dto.CreateGameRequestDto;
import com.bol.nordonezc.mancala.dto.PlayRequestDto;
import com.bol.nordonezc.mancala.dto.Response;
import com.bol.nordonezc.mancala.mappers.BoardMapper;
import com.bol.nordonezc.mancala.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/game")
public class GameController {

    private final GameService gameService;
    private final BoardMapper mapper;

    @PostMapping("")
    public ResponseEntity<Response<UUID>> createGame(@RequestBody(required = false) CreateGameRequestDto requestDto) {
        var gameInput = Optional.ofNullable(requestDto).orElse(new CreateGameRequestDto());
        return ResponseEntity.ok(new Response<>(gameService.createGame(gameInput.getStones())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<BoardDto>> getGame(@PathVariable UUID id) {
        BoardDto boardDto = mapper.map(gameService.getGame(id));
        return ResponseEntity.ok(
                new Response<>(boardDto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Response<BoardDto>> playGame(@PathVariable UUID id,
                                                       @RequestBody
                                                       @Valid
                                                       PlayRequestDto requestDto) {
        BoardDto boardDto = mapper.map(gameService.playGame(id, requestDto.getPosition()));
        boardDto.setId(id);
        return ResponseEntity.ok(
                new Response<>(boardDto));
    }
}