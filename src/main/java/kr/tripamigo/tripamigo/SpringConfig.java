package kr.tripamigo.tripamigo;

import kr.tripamigo.tripamigo.repository.BoardRepository;
import kr.tripamigo.tripamigo.repository.UserRepository;
import kr.tripamigo.tripamigo.service.BoardService;
import kr.tripamigo.tripamigo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringConfig {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Bean
    public UserService userService() {
        return new UserService(userRepository);
    }

    @Bean
    public BoardService boardService() {
        return new BoardService(boardRepository);
    }

}
