package kr.tripamigo.tripamigo.service;
/*
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import kr.tripamigo.tripamigo.domain.Diary;
import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Board;
import kr.tripamigo.tripamigo.domain.board.Magazine;
import kr.tripamigo.tripamigo.dto.DiaryFormDTO;
import kr.tripamigo.tripamigo.dto.MagazineFormDTO;
import kr.tripamigo.tripamigo.exception.NoPageException;
import kr.tripamigo.tripamigo.repository.BoardRepository;
import kr.tripamigo.tripamigo.repository.DiaryRepository;
import kr.tripamigo.tripamigo.repository.MagazineRepository;
import lombok.RequiredArgsConstructor;


@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final BoardRepository boardRepository;
    private final DiaryRepository diaryRepository;

    @Autowired
    private MessageSource messageSource;

    public void writeDiary(DiaryFormDTO diaryFormDTO, User user) {
        Diary diary = new Diary();
        diary.setUser(user);
        diary.setBoardSubject(diaryFormDTO.getSubject());
        diary.setBoardContent(diaryFormDTO.getContent());
        diary.setBoardThumbnail(diaryFormDTO.getThumbnail());
        diary.setBoardTag(diaryFormDTO.getTags());
        diaryRepository.save(diary);
    }
    
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
                    .getMessage("error.404", null, Locale.getDefault()), "/community/home");
                });
    	
    	return diary;
    	
    	
    }

	public void delete(Board board) {
		boardRepository.save(board);
	}

	public void boardHitsUp(Long boardSeq) {
		boardRepository.boardHitsUp(boardSeq);
	}
	
}
*/