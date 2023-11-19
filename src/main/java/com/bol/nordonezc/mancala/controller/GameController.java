package com.bol.nordonezc.mancala.controller;

import com.bol.nordonezc.mancala.dto.CreateGameRequestDto;
import com.bol.nordonezc.mancala.dto.GameDto;
import com.bol.nordonezc.mancala.dto.GenericResponse;
import com.bol.nordonezc.mancala.dto.PlayRequestDto;
import com.bol.nordonezc.mancala.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin
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
        GameDto gameCreated = gameService.createGame(gameInput.getStones());
        GenericResponse<GameDto> body = new GenericResponse<>(gameCreated);
        return generateLink(body, gameCreated.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<GameDto>> getGame(@PathVariable UUID id) {
        GenericResponse<GameDto> body = new GenericResponse<>(gameService.getGame(id));
        return generateLink(body, id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<GenericResponse<GameDto>> playGame(@PathVariable UUID id,
                                                             @RequestBody
                                                             @Valid
                                                             PlayRequestDto requestDto) {
        GenericResponse<GameDto> body = new GenericResponse<>(gameService.playGame(id, requestDto.getPosition()));
        return generateLink(body, id);
    }

    /**
     * Response given to consult the root
     *
     * @return welcome message
     */
    @GetMapping("/")
    public List<Link> home() {
        List<Link> availableOperations = new ArrayList<>();
        availableOperations.add(linkTo(
                methodOn(GameController.class)
                        .createGame(null)).withSelfRel());
        availableOperations.add(linkTo(
                methodOn(GameController.class)
                        .getGame(null)).withSelfRel());
        availableOperations.add(linkTo(
                methodOn(GameController.class)
                        .playGame(null, new PlayRequestDto())).withSelfRel());
        return availableOperations;
    }

    private ResponseEntity<GenericResponse<GameDto>> generateLink(GenericResponse<GameDto> genericResponse, UUID id) {
        var selfLink = linkTo(GameController.class).slash(id).withSelfRel();
        var homeLink = linkTo(methodOn(GameController.class).home()).withRel("home");
        return ResponseEntity.ok(genericResponse.add(selfLink, homeLink));
    }
}