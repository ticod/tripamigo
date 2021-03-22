package kr.tripamigo.tripamigo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.tripamigo.tripamigo.domain.board.Info;

public interface InfoJpaRepository
        extends JpaRepository<Info, Long>, InfoRepository {
}
