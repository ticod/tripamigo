package kr.tripamigo.tripamigo.service;

import kr.tripamigo.tripamigo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/**
 * 여행 후기, 매거진, 여행 홍보 게시글
 */
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

}
