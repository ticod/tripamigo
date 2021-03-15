package kr.tripamigo.tripamigo.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import kr.tripamigo.tripamigo.domain.Comment;
import kr.tripamigo.tripamigo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public class CommentService {
	
	private final CommentRepository commentRepository;
	
	public List<Comment> commentList(int type, Long seq){
		return commentRepository.findByBoard(type, seq);
	}
	
	
}
