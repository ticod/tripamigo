package kr.tripamigo.tripamigo;

import kr.tripamigo.tripamigo.repository.UserRepository;
import kr.tripamigo.tripamigo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringConfig {

    private final UserRepository userRepository;

    @Bean
    public UserService userService() {
        return new UserService(userRepository);
    }

}
