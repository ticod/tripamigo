package kr.tripamigo.tripamigo.interceptor;

import kr.tripamigo.tripamigo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private MessageSource messageSource;

    // 로그인 체크 메서드
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler)
            throws Exception {

        HttpSession session = request.getSession();

        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
    
    // 인터셉터 적용할 url 설정
    public List<String> getAddPath() {
        return Arrays.asList("/community/**", "/mypage/**");
    }

    // 인터셉터 적용 제외할 url 설정
    public List<String> getExcludePath() {
        return Arrays.asList("/community/magazine", 
                "/community/magazinePage**", 
                "/community/ckeditor**");
    }

}
