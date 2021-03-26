package kr.tripamigo.tripamigo.repository;

import kr.tripamigo.tripamigo.domain.Recommend;
import kr.tripamigo.tripamigo.domain.RecommendType;
import kr.tripamigo.tripamigo.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
public class RecommendRepositoryTest {

    @Autowired RecommendRepository recommendRepository;
    @Autowired UserRepository userRepository;

    private List<User> users;

    @BeforeEach
    void before() {

        users = userRepository.findAll();
        Recommend recommend1 = new Recommend();
        recommend1.setContentSeq(1L);
        recommend1.setRecommendType(RecommendType.BOARD);
        recommend1.setUserSeq(users.get(0).getUserSeq());
        recommend1.setDate(LocalDateTime.now());
        recommend1.setStatus(true);
        recommendRepository.save(recommend1);


        Recommend recommend2 = new Recommend();
        recommend2.setContentSeq(1L);
        recommend2.setRecommendType(RecommendType.BOARD);
        recommend2.setUserSeq(users.get(1).getUserSeq());
        recommend2.setDate(LocalDateTime.now());
        recommend2.setStatus(true);
        recommendRepository.save(recommend2);

    }

    @Test
    void repositoryCountByRecommendTypeAndSeq() {
        int count = recommendRepository.countByRecommendTypeAndContentSeqAndStatus(RecommendType.BOARD, 1L, true);
        Assertions.assertThat(count).isEqualTo(2);
    }

    @Test
    void userRecommendCount() {
        int count = recommendRepository.countByUserSeqAndStatus(users.get(0).getUserSeq(), true);
        Assertions.assertThat(count).isEqualTo(1);
    }

}
