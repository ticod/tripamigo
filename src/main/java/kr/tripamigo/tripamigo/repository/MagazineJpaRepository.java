package kr.tripamigo.tripamigo.repository;

import kr.tripamigo.tripamigo.domain.board.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagazineJpaRepository
        extends JpaRepository<Magazine, Long>, MagazineRepository {
}
