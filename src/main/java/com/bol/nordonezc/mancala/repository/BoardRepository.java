package com.bol.nordonezc.mancala.repository;

import com.bol.nordonezc.mancala.entities.BoardEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BoardRepository extends CrudRepository<BoardEntity, String> {

    Set<BoardEntity> findAllByWinner(int winner);
}
