package kr.tripamigo.tripamigo.repository;

import kr.tripamigo.tripamigo.domain.board.Board;
import kr.tripamigo.tripamigo.domain.board.Magazine;

import java.util.List;
import java.util.Optional;

public interface MagazineRepository {

    List<Magazine> findAll();
    Optional<Magazine> findById(Long boardSeq);

}
