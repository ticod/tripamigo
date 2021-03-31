package kr.tripamigo.tripamigo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.tripamigo.tripamigo.domain.DiaryBoard;

public interface DiaryBoardJpaRepository
        extends JpaRepository<DiaryBoard, Long>, DiaryBoardRepository{
}
