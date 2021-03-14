package kr.tripamigo.tripamigo.aop;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.exception.LoginException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Locale;

@Component
@Aspect
public class UserLoginAspect {

    @Autowired
    private MessageSource messageSource;

    @Around("execution(* kr.tripamigo.tripamigo.controller.*.*AndRequireLogin(..)) && args(..,session)")
    public Object userLoginCheck(ProceedingJoinPoint joinPoint, HttpSession session)
            throws Throwable {

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new LoginException(
                    messageSource.getMessage("error.login.required", null, Locale.getDefault())
                    , "/login"
            );
        }
        return joinPoint.proceed();

    }

}
