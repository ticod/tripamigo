package kr.tripamigo.tripamigo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.tripamigo.tripamigo.domain.Comment;

public interface CommentJpaRepository extends JpaRepository<Comment, Long>, CommentRepository{

}
