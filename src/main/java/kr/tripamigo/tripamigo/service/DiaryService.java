package kr.tripamigo.tripamigo.service;

import kr.tripamigo.tripamigo.domain.Diary;
import kr.tripamigo.tripamigo.repository.DiaryBoardRepository;
import kr.tripamigo.tripamigo.repository.DiaryRepository;
import org.springframework.transaction.annotation.Transactional;

import kr.tripamigo.tripamigo.domain.DiaryBoard;
import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.dto.DiaryFormDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final DiaryBoardRepository diaryBoardRepository;

    public List<Diary> getDiaryList(User user) {

        return diaryRepository.findAllByUserAndStatusOrderBySeq(user, true);
    }

}