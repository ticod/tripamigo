package kr.tripamigo.tripamigo.service;

import kr.tripamigo.tripamigo.domain.Diary;
import kr.tripamigo.tripamigo.repository.DiaryBoardRepository;
import kr.tripamigo.tripamigo.repository.DiaryRepository;
import org.springframework.transaction.annotation.Transactional;

import kr.tripamigo.tripamigo.domain.DiaryBoard;
import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.dto.DiaryFormDTO;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final DiaryBoardRepository diaryBoardRepository;
    public void writeDiary(DiaryFormDTO diaryFormDTO, User user) {
        DiaryBoard diaryBoard = new DiaryBoard();
        diaryBoard.setDiarySubject(diaryFormDTO.getSubject());
        diaryBoard.setDiaryContent(diaryFormDTO.getContent());
        //diaryBoard.setDiaryThumbnail(diaryFormDTO.getThumbnail());
        diaryRepository.save(diaryBoard);
    }

//    public List<Diary> diaryList(){
//    	List<Diary> diaryList;
//    	diaryList = diaryRepository.findAllByBoardStatus(true);
//    	return diaryList;
//    }
//
//    public Diary readdiary(Long boardSeq) {
//    	Diary diary = diaryRepository
//                .findById(boardSeq)
//                .orElseGet(() -> {
//                    throw new NoPageException(messageSource
//                    .getMessage("error.404", null, Locale.getDefault()), "/mypage");
//                });
//
//    	return diary;
//
//
//    }
//
//	public void delete(Board board) {
//		boardRepository.save(board);
//	}
//
//	public void boardHitsUp(Long boardSeq) {
//		boardRepository.boardHitsUp(boardSeq);
//	}

}