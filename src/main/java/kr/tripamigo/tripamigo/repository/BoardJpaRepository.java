package kr.tripamigo.tripamigo.repository;

import kr.tripamigo.tripamigo.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepository
        extends JpaRepository<Board, Long>, BoardRepository {
}
