package kr.tripamigo.tripamigo.repository;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Board;
import kr.tripamigo.tripamigo.domain.board.Magazine;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
public class BoardRepositoryTest {

    @Autowired BoardRepository boardRepository;
    @Autowired UserRepository userRepository;

    @Test
    public void saveAndGetMagazine() {

        User dbUser = userRepository.findById(1L).get();

        Magazine magazine1 = new Magazine();
        magazine1.setUser(dbUser);
        magazine1.setBoardSubject("제목제목");
        magazine1.setBoardContent("내용내용");
        magazine1.setBoardThumbnail("asdf.jpg");
        magazine1.setBoardTag("어디,어디,어디,어디");

        Magazine magazine2 = new Magazine();
        magazine2.setUser(dbUser);
        magazine2.setBoardSubject("제목제목");
        magazine2.setBoardContent("내용내용");
        magazine2.setBoardThumbnail("asdf.jpg");
        magazine2.setBoardTag("어디,어디,어디,어디");

        Board saveBoard1 = boardRepository.save(magazine1);
        Board saveBoard2 = boardRepository.save(magazine2);

        Assertions.assertThat(saveBoard1.getBoardSeq()).isNotEqualTo(null);
        Assertions.assertThat(saveBoard2.getBoardSeq()).isNotEqualTo(null);

        System.out.println("Magazine1: " + saveBoard1);
        System.out.println("Magazine2: " + saveBoard2);

        List<Magazine> findMagazines = boardRepository.findAll().stream().map((board) -> (Magazine) board).collect(Collectors.toList());

        Assertions.assertThat(findMagazines.get(0)).isNotEqualTo(null);
        Assertions.assertThat(findMagazines.get(1)).isNotEqualTo(null);

    }

}
