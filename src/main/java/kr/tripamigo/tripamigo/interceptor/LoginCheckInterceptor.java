package kr.tripamigo.tripamigo.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler)
            throws Exception {

        HttpSession session = request.getSession();

        return false;
    }

    public List<String> getAddPath() {
        return Arrays.asList("/community/**");
    }

    public List<String> getExcludePath() {
        return Arrays.asList();
    }

}
