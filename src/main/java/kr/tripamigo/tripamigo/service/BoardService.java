package kr.tripamigo.tripamigo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.tripamigo.tripamigo.dto.BoardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Board;
import kr.tripamigo.tripamigo.domain.board.Info;
import kr.tripamigo.tripamigo.domain.board.Magazine;
import kr.tripamigo.tripamigo.domain.board.Plan;
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
	private static final int BLOCK_PAGE_NUM_COUNT = 4; // 블럭에 존재하는 페이지 수
	private static final int PAGE_POST_COUNT = 8; //한페이지에 존재하는 게시글 수

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
    
    public List<Magazine> magazineListAllPaging(Pageable pageable) {
        return magazineRepository.findAllByBoardStatusOrderByBoardSeqDesc(true, pageable).toList();
    }
    
    public int getMagazineCount() {
    	int magazineCnt = magazineRepository.countAllByBoardStatus(true);
    	return magazineCnt;
    }
    public Integer[] getPageList(Pageable pageable, String findString) {
    
    	int curPageNum = pageable.getPageNumber();
    	Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];
    	double postsTotalCount;
    	if(findString==null) {
    		postsTotalCount = Double.valueOf(this.getMagazineCount());// 총 게시글 수
    	}else {
    		postsTotalCount = Double.valueOf(this.getMagazineSearchCount(findString));
    	}
    	int totalLastPageNum = (int)(Math.ceil(postsTotalCount/PAGE_POST_COUNT)); //총 게시글 수를 기준으로 계산한 마지막 페이지 번호 계산
    	int blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)? curPageNum+BLOCK_PAGE_NUM_COUNT : totalLastPageNum;    	// 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산 // 4페이지가 현재페이지(2페이지 + 4 = 6페이지)보다 크면? 마지막페이지는 6페이지, 아니면 4페이지가 끝.
    	curPageNum = (curPageNum<=3)? 0: curPageNum-3;    	// 페이지 시작 번호 조정 // 블록에서의 페이지 시작번호는 현재페이지가 3보다 작을때까지 0부터 시작, 4면 3부터 시작?
    	for(int val=curPageNum, i=0; val <= blockLastPageNum-1; val++, i++) {    	// 페이지 번호 할당
    		pageList[i] = val;
    	}
    	return pageList;
    }
    
    
    
    public List<Magazine> magazineListAllPagingAndFind(Pageable pageable, String findString) {
    	List<Magazine> magazineList;
    	magazineList = magazineRepository.findAllByBoardStatusAndBoardContentContainingOrBoardStatusAndBoardSubjectContainingOrderByBoardSeqDesc(true, findString, true, findString, pageable).toList();
    	return magazineList;
	}
    public int getMagazineSearchCount(String findString) {
    	int magazineCnt = magazineRepository.countAllByBoardStatusAndBoardContentContainingOrBoardStatusAndBoardSubjectContainingOrderByBoardSeqDesc(true, findString, true, findString);
    	return magazineCnt;
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
