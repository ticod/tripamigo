package kr.tripamigo.tripamigo.repository;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Board;
import kr.tripamigo.tripamigo.domain.board.Magazine;
import kr.tripamigo.tripamigo.domain.board.Promotion;
import kr.tripamigo.tripamigo.domain.board.PromotionType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
public class BoardRepositoryTest {

    @Autowired BoardRepository boardRepository;
    @Autowired UserRepository userRepository;
    private User user;

    @BeforeEach
    void beforeEach() {
        user = userRepository.findById(1L).get();
    }

    @Test
    public void saveAndGetMagazine() {

        Magazine magazine1 = new Magazine();
        magazine1.setUser(user);
        magazine1.setBoardSubject("제목제목");
        magazine1.setBoardContent("내용내용");
        magazine1.setBoardThumbnail("asdf.jpg");
        magazine1.setBoardTag("어디,어디,어디,어디");

        Magazine magazine2 = new Magazine();
        magazine2.setUser(user);
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

    @Test
    public void saveAndGetPromotion() {

        Promotion promotion1 = new Promotion();
        promotion1.setUser(user);
        promotion1.setBoardSubject("제목제목");
        promotion1.setBoardContent("내용내용");
        promotion1.setBoardThumbnail("asdf.jpg");
        promotion1.setBoardTag("어디,어디,어디,어디");
        promotion1.setBoardCategory(PromotionType.FOOD);

        Promotion promotion2 = new Promotion();
        promotion2.setUser(user);
        promotion2.setBoardSubject("제목제목");
        promotion2.setBoardContent("내용내용");
        promotion2.setBoardThumbnail("asdf.jpg");
        promotion2.setBoardTag("어디,어디,어디,어디");
        promotion2.setBoardCategory(PromotionType.ATTRACTIONS);

        Promotion savePromotion = (Promotion) boardRepository.save(promotion1);
        Board saveBoard = boardRepository.save(promotion2);

        Assertions.assertThat(savePromotion.getBoardSeq()).isNotEqualTo(null);
        Assertions.assertThat(saveBoard.getBoardSeq()).isNotEqualTo(null);

        System.out.println("Promotion: " + savePromotion);
        System.out.println("Board: " + saveBoard);

        List<Promotion> findPromotions = boardRepository.findAll().stream().map((board) -> (Promotion) board).collect(Collectors.toList());

        Assertions.assertThat(findPromotions.get(0)).isNotEqualTo(null);
        Assertions.assertThat(findPromotions.get(1)).isNotEqualTo(null);

    }

}
