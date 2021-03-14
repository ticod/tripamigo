package kr.tripamigo.tripamigo;

import kr.tripamigo.tripamigo.repository.BoardRepository;
import kr.tripamigo.tripamigo.repository.MagazineRepository;
import kr.tripamigo.tripamigo.repository.UserRepository;
import kr.tripamigo.tripamigo.service.BoardService;
import kr.tripamigo.tripamigo.service.UserService;
import kr.tripamigo.tripamigo.util.CipherUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

@Configuration
@EnableAspectJAutoProxy
@RequiredArgsConstructor
public class SpringConfig {

    /* Repository */
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final MagazineRepository magazineRepository;

    /* Util */
    private final CipherUtil cipherUtil;

    @Bean
    public HandlerExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver ser = new SimpleMappingExceptionResolver();
        Properties props = new Properties();
        props.put("exception.LoginException", "error/alert");
        ser.setExceptionMappings(props);
        return ser;
    }

    /* Service */
    @Bean
    public UserService userService() {
        return new UserService(userRepository);
    }

    @Bean
    public BoardService boardService() {
        return new BoardService(boardRepository, magazineRepository);
    }

}
