package kr.tripamigo.tripamigo.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import kr.tripamigo.tripamigo.domain.Comment;
import kr.tripamigo.tripamigo.domain.User;
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
	public void deleteComment(Long commentSeq) {
		commentRepository.deleteById(commentSeq);
	}
	
	
}
