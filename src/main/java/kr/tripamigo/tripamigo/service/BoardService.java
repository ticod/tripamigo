package kr.tripamigo.tripamigo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.tripamigo.tripamigo.dto.BoardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Board;
import kr.tripamigo.tripamigo.domain.board.Info;
import kr.tripamigo.tripamigo.domain.board.Magazine;
import kr.tripamigo.tripamigo.dto.MagazineFormDTO;
import kr.tripamigo.tripamigo.exception.NoPageException;
import kr.tripamigo.tripamigo.repository.BoardRepository;
import kr.tripamigo.tripamigo.repository.InfoRepository;
import kr.tripamigo.tripamigo.repository.MagazineRepository;
import lombok.RequiredArgsConstructor;

/**
 * 여행 후기, 매거진, 여행 홍보 게시글
 */
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MagazineRepository magazineRepository;

    @Autowired
    private MessageSource messageSource;

    public void writeMagazine(MagazineFormDTO magazineFormDTO, User user) {
        Magazine magazine = new Magazine();
        magazine.setUser(user);
        magazine.setBoardSubject(magazineFormDTO.getSubject());
        magazine.setBoardContent(magazineFormDTO.getContent());
        magazine.setBoardThumbnail(magazineFormDTO.getThumbnail());
        magazine.setBoardTag(magazineFormDTO.getTags());
        
        boardRepository.save(magazine);
    }
    
    public List<Magazine> magazineList(){
    	List<Magazine> magazineList;
    	magazineList = magazineRepository.findAllByBoardStatusOrderByBoardSeqDesc(true);
    	return magazineList;
    } 
    
    public Magazine readMagazine(Long boardSeq) {
    	Magazine magazine = magazineRepository
                .findById(boardSeq)
                .orElseGet(() -> {
                    throw new NoPageException(messageSource
                    .getMessage("error.404", null, Locale.getDefault()), "/community/home");
                });
    	
    	return magazine;
    	
    	
    }

	public void delete(Board board) {
		boardRepository.save(board);
	}

	public void boardHitsUp(Long boardSeq) {
		boardRepository.boardHitsUp(boardSeq);
	}

	public void updateMagazine(Magazine dbMagazine) {
		boardRepository.save(dbMagazine);
	}

	public Map<BoardType, List<Board>> boardList() {
        Map<BoardType, List<Board>> result = new HashMap<>();
        return result;
    }

}
