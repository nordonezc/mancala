package com.bol.nordonezc.mancala.service;

import com.bol.nordonezc.mancala.business.MancalaBoard;
import com.bol.nordonezc.mancala.dto.GameDto;
import com.bol.nordonezc.mancala.entities.BoardEntity;
import com.bol.nordonezc.mancala.exceptions.BoardException;
import com.bol.nordonezc.mancala.mappers.BoardMapper;
import com.bol.nordonezc.mancala.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.bol.nordonezc.mancala.utils.ErrorMessage.*;
import static com.bol.nordonezc.mancala.utils.PitUtils.*;


@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final BoardRepository boardRepository;

    private final BoardMapper boardMapper;

    @Override
    public GameDto createGame(int stones) {
        UUID newBoardID = UUID.randomUUID();
        var boardToSave = boardMapper.mapBoardToEntity(newBoardID, new MancalaBoard(stones));
        boardToSave.setCreationDate(LocalDateTime.now());
        boardToSave.setModificationDate(LocalDateTime.now());
        var boardCreated = boardRepository.save(boardToSave);
        return boardMapper.mapEntityToDto(boardCreated);
    }

    @Override
    public GameDto getGame(UUID boardId) {
        return boardMapper.mapEntityToDto(getGameBoard(boardId));
    }

    @Override
    public GameDto playGame(UUID boardId, int position) {
        var boardFound = getGameBoard(boardId);
        var boardToPlay = boardMapper.mapEntityToBoard(boardFound);
        int positionToPlay = getPositionToPlay(position, boardToPlay.getPlayerTurn());

        if (boardToPlay.getPits()[positionToPlay] == EMPTY_PIT) {
            throw new BoardException(EMPTY_POSITION);
        }

        boardToPlay.playTurn(positionToPlay);

        var entityBoard = boardMapper.mapBoardToEntity(boardId, boardToPlay);
        updateEntity(entityBoard);
        return boardMapper.mapEntityToDto(entityBoard);
    }

    /**
     * Check if the actual entity has defined a winner to update it or delete it
     * @param entityBoard - Board to check winner
     */
    private void updateEntity(BoardEntity entityBoard) {
        if(entityBoard.getWinner() != 0){
            boardRepository.deleteById(entityBoard.getId());
        } else{
            entityBoard.setModificationDate(LocalDateTime.now());
            boardRepository.save(entityBoard);
        }
    }

    /**
     * Determine the position to play based on the current player turn and subtracting 1 from the request
     *
     * @param position   - Position given in the request that could be between 1 and 6
     * @param playerTurn - Actual board to check the player turn
     * @return The position that would be used to play in the MancalaBoard
     */
    private static int getPositionToPlay(int position, int playerTurn) {
        return playerTurn == FIRST_PLAYER ?
                position - 1 :
                position - 1 + 7;
    }

    private BoardEntity getGameBoard(UUID boardId) {
        var boardToPlay = this.boardRepository.findById(boardId.toString());

        if (boardToPlay.isEmpty()) {
            throw new BoardException(NO_BOARD_FOUND);
        }

        return boardToPlay.get();
    }
}