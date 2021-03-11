package kr.tripamigo.tripamigo.repository;

import kr.tripamigo.tripamigo.domain.board.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    Board save(Board board);
    Optional<Board> findById(Long id);
    List<Board> findAll();

}
