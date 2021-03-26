package kr.tripamigo.tripamigo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.tripamigo.tripamigo.domain.Comment;
import kr.tripamigo.tripamigo.domain.RecommendType;

public interface CommentRepository {
	
	//jpql 테이블기준 x,  entity 기준o => _ 안씀. 낙타표기법.
	@Query("select c from Comment as c where c.commentContentType=:type and c.commentContentSeq=:seq and c.commentStatus=1 order by c.commentSeq desc")
	List<Comment> findByBoard(@Param("type") int type, @Param("seq") Long seq);
	
	Comment save(Comment comment);

	void deleteById(Long commentSeq);

	Comment findByCommentSeq(Long commentSeq);
	
	int countByCommentContentTypeAndCommentContentSeqAndCommentStatus(int commentContentType, Long commentContentSeq, boolean status);
}

