package kr.tripamigo.tripamigo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import kr.tripamigo.tripamigo.domain.DiaryBoard;
import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.dto.DiaryFormDTO;
//import kr.tripamigo.tripamigo.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;


@Transactional
@RequiredArgsConstructor
public class DiaryService {

//    private final DiaryRepository diaryRepository;

    @Autowired
    private MessageSource messageSource;
/*
    public void writeDiary(DiaryFormDTO diaryFormDTO, User user) {
        DiaryBoard diaryBoard = new DiaryBoard();
        diaryBoard.setDiarySubject(diaryFormDTO.getSubject());
        diaryBoard.setDiaryContent(diaryFormDTO.getContent());
        //diaryBoard.setDiaryThumbnail(diaryFormDTO.getThumbnail());
        diaryRepository.save(diaryBoard);
    }
    /*
    public List<Diary> diaryList(){
    	List<Diary> diaryList;
    	diaryList = diaryRepository.findAllByBoardStatus(true);
    	return diaryList;
    } 
    
    public Diary readdiary(Long boardSeq) {
    	Diary diary = diaryRepository
                .findById(boardSeq)
                .orElseGet(() -> {
                    throw new NoPageException(messageSource
                    .getMessage("error.404", null, Locale.getDefault()), "/mypage");
                });
    	
    	return diary;
    	
    	
    }

	public void delete(Board board) {
		boardRepository.save(board);
	}

	public void boardHitsUp(Long boardSeq) {
		boardRepository.boardHitsUp(boardSeq);
	}
	*/
}