package kr.tripamigo.tripamigo.repository;

import java.util.List;
import java.util.Optional;

import kr.tripamigo.tripamigo.domain.Diary;
import kr.tripamigo.tripamigo.domain.User;

public interface DiaryRepository {

    Diary save(Diary diary);
    List<Diary> findAllByUserAndStatusOrderBySeq(User user, boolean status);

}