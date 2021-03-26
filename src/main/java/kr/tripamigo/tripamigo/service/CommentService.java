package kr.tripamigo.tripamigo.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import kr.tripamigo.tripamigo.domain.Comment;
import kr.tripamigo.tripamigo.domain.RecommendType;
import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Info;
import kr.tripamigo.tripamigo.dto.CommentFormDTO;
import kr.tripamigo.tripamigo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public class CommentService {
	
	private final CommentRepository commentRepository;
	
	public List<Comment> commentList(int type, Long seq){
		return commentRepository.findByBoard(type, seq);
	}
	public void writeComment(int contentType, CommentFormDTO commentFormDTO, User user) {
		Comment comment = new Comment();
		
		comment.setCommentContentType(contentType);
		comment.setCommentContentSeq(Long.parseLong(commentFormDTO.getBoardSeq()));
		comment.setUser(user);
		comment.setCommentContent(commentFormDTO.getContent());
		
		commentRepository.save(comment);
	}
	public void deleteComment(Comment comment) {
		commentRepository.save(comment);
	}
	
	public Comment readComment(Long commentSeq) {
		return commentRepository.findByCommentSeq(commentSeq);
	}
	
	public Map<Info, Integer> countCommentList(List<Info> infoList) {
		
		Map<Info, Integer> infoCommentMap = new TreeMap<Info, Integer>((o1, o2)-> o2.getInfoSeq().intValue()-o1.getInfoSeq().intValue());
		
		for(Info i : infoList) {
			int cnt = commentRepository.countByCommentContentTypeAndCommentContentSeqAndCommentStatus(2, i.getInfoSeq(), true);
			infoCommentMap.put(i, cnt);
		}
		System.out.println("Info 댓글 수 : \n"+infoCommentMap);
		
		return infoCommentMap;
	}
	
}
