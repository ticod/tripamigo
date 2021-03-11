package kr.tripamigo.tripamigo.aop;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.exception.LoginException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
@Aspect
public class UserLoginAspect {

    @Around("execution(* controller.*.loginCheck*(..)) && args(..,session)")
    public Object userLoginCheck(ProceedingJoinPoint joinPoint, HttpSession session)
            throws Throwable {

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new LoginException("[USER LOGIN] 로그인 하세요", "/login");
        }
        return joinPoint.proceed();

    }

}
