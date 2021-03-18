package kr.tripamigo.tripamigo;

import java.util.Properties;

import kr.tripamigo.tripamigo.interceptor.LoginCheckInterceptor;
import kr.tripamigo.tripamigo.repository.*;
import kr.tripamigo.tripamigo.service.*;
import org.json.simple.parser.JSONParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import kr.tripamigo.tripamigo.util.CipherUtil;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableAspectJAutoProxy
@RequiredArgsConstructor
public class SpringConfig implements WebMvcConfigurer {
	
    /* Repository */
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final MagazineRepository magazineRepository;
    private final CommentRepository commentRepository;
    private final RecommendRepository recommendRepository;
    private final PlanRepository planRepository;

    /*** Bean ***/
    /* Exception */
    @Bean
    public HandlerExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver ser = new SimpleMappingExceptionResolver();
        Properties props = new Properties();
        props.put("exception.LoginException", "error/alert");
        props.put("exception.NoPageException", "error/alert");
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

    @Bean
    public CommentService commentService() {
    	return new CommentService(commentRepository);
    }

    @Bean
    public RecommendService recommendService() {
    	return new RecommendService(recommendRepository);
    }

    @Bean
    public PlanService planService() {
        return new PlanService(planRepository);
    }

    /* Etc */
    @Bean
    public JSONParser jsonParser() {
        return new JSONParser();
    }

    /* Interceptor */
    public void addInterceptors(InterceptorRegistry registry) {
        LoginCheckInterceptor loginCheckInterceptor = new LoginCheckInterceptor();
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns(loginCheckInterceptor.getAddPath())
                .excludePathPatterns(loginCheckInterceptor.getExcludePath());
    }

}
