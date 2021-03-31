package kr.tripamigo.tripamigo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.tripamigo.tripamigo.domain.Diary;

public interface DiaryJpaRepository
        extends JpaRepository<Diary, Long>, DiaryRepository{
}