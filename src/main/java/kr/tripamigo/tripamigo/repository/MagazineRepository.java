package kr.tripamigo.tripamigo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import kr.tripamigo.tripamigo.domain.board.Magazine;

public interface MagazineRepository {

    List<Magazine> findAllByBoardStatusOrderByBoardSeqDesc(boolean status);
    Optional<Magazine> findById(Long boardSeq);
    Page<Magazine> findAllByBoardStatusOrderByBoardSeqDesc(boolean status, Pageable pageable);
	int countAllByBoardStatus(boolean status);
	
//	@Query("select b from Board as b where b.boardStatus=:status and b.boardContent=:find")
	Page<Magazine> findAllByBoardStatusAndBoardContentContainingOrBoardStatusAndBoardSubjectContainingOrderByBoardSeqDesc(boolean status, String boardContent, boolean status2, String boardSubject, Pageable pageable);
	int countAllByBoardStatusAndBoardContentContainingOrBoardStatusAndBoardSubjectContainingOrderByBoardSeqDesc(
			boolean status, String boardContent, boolean status2, String boardSubject);

}
