package kr.tripamigo.tripamigo.repository;

import kr.tripamigo.tripamigo.domain.board.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MagazineJpaRepository
        extends JpaRepository<Magazine, Long>, MagazineRepository {
}
