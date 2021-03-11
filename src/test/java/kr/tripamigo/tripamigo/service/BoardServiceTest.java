package kr.tripamigo.tripamigo.service;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Magazine;
import kr.tripamigo.tripamigo.dto.MagazineFormDTO;
import kr.tripamigo.tripamigo.repository.BoardRepository;
import kr.tripamigo.tripamigo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired BoardRepository boardRepository;
    @Autowired UserRepository userRepository;
    @Autowired BoardService boardService;

    @Test
    void writeMagazineTest() {
        User user = userRepository.findById(1L).get();
        MagazineFormDTO magazineFormDTO = new MagazineFormDTO(
                "제목", "내용", "지역1,지역2", "썸네일"
        );
        boardService.writeMagazine(magazineFormDTO, user);

        Magazine magazine = (Magazine) boardRepository.findByBoardSubject("제목")
                .stream().findFirst().get();
        Assertions.assertThat(magazine).isNotEqualTo(null);
        System.out.println("magazine: " + magazine);
    }

}
