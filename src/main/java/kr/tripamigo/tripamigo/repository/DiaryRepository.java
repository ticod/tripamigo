package kr.tripamigo.tripamigo.repository;

import java.util.List;

import kr.tripamigo.tripamigo.domain.Diary;
import kr.tripamigo.tripamigo.domain.User;

public interface DiaryRepository {

    List<Diary> findAllByUserAndStatusOrderBySeq(User user, boolean status);

}