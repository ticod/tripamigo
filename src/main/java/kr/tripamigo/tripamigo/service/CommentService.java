package kr.tripamigo.tripamigo.service;

import org.springframework.transaction.annotation.Transactional;

import kr.tripamigo.tripamigo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public class CommentService {
	
	private final CommentRepository commentRepository;
	
	
	
}
