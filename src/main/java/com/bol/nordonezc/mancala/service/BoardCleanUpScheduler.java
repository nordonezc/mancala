package com.bol.nordonezc.mancala.service;

import com.bol.nordonezc.mancala.entities.BoardEntity;
import com.bol.nordonezc.mancala.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.BiPredicate;

@Component
@RequiredArgsConstructor
public class BoardCleanUpScheduler {

    private final BoardRepository boardRepository;

    private final BiPredicate<BoardEntity, LocalDateTime> IS_BEFORE_THAN =
            (boardEntity, retentionPolicy) -> Optional.ofNullable(boardEntity.getModificationDate())
                    .orElse(LocalDateTime.MIN)
                    .isBefore(retentionPolicy);

    /**
     * Schedule a deletion of the keys every day at midnight
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanUpData() {
        LocalDateTime retentionPolicy = LocalDateTime.now().minusDays(1);
        boardRepository.findAllByWinner(0).stream()
                .filter(boardEntity -> IS_BEFORE_THAN.test(boardEntity, retentionPolicy))
                .forEach(boardRepository::delete);
    }
}
