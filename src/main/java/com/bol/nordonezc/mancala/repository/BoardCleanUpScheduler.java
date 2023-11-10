package com.bol.nordonezc.mancala.repository;

import com.bol.nordonezc.mancala.entities.BoardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardCleanUpScheduler {

    private final BoardRepository boardRepository;

    /**
     * Schedule a deletion of the keys every minute
     */
    @Scheduled(fixedRate = 60000)
    public void cleanupRedisData() {
        var boardsToClean = boardRepository.findAllByWinner(1);
        boardsToClean.addAll(boardRepository.findAllByWinner(2));
        for (BoardEntity key : boardsToClean) {
            boardRepository.delete(key);
        }
    }
}
