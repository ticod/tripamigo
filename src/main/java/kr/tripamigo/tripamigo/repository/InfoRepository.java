package kr.tripamigo.tripamigo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.tripamigo.tripamigo.domain.board.Info;

public interface InfoRepository {

	List<Info> findAllByInfoStatusOrderByInfoSeqDesc(boolean status);
	Info save(Info info);

	List<Info> findAllByInfoStatusAndAreaNameAndAreaAddress(boolean status, String areaName, String areaAddress);
//    Board save(Board board);
//    Optional<Board> findById(Long id);
//    List<Board> findAll();
//    List<Board> findByBoardSubject(String subject);
//	
//	@Modifying
//	@Query("update Board b set b.boardHits=b.boardHits+1 where b.boardSeq=:boardSeq")
//	void boardHitsUp(@Param("boardSeq") Long boardSeq);
//	
	Optional<Info> findById(Long iSeq);
	
	@Modifying
	@Query("update Info i set i.infoHits=i.infoHits+1 where i.infoSeq=:infoSeq")
	void hitsUp(@Param("infoSeq") Long infoSeq);
	
	
}
