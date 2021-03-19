package kr.tripamigo.tripamigo.repository;

import kr.tripamigo.tripamigo.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void listCount() {
        int count = userRepository.countByUserStatus(true);
        List<User> users = userRepository.findAllByUserStatus(true);

        Assertions.assertThat(users.size()).isEqualTo(count);
    }

}
