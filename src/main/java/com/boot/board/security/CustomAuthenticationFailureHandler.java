package com.boot.board.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

 
//}

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof BadCredentialsException && "비밀번호 불일치".equals(exception.getMessage())) {
            request.setAttribute("errorPassword", exception.getMessage());
        } else if(exception instanceof UsernameNotFoundException) {
            request.setAttribute("errorId", exception.getMessage());
        } else {
            request.setAttribute("errorAccount", "로그인 실패: " + exception.getMessage());
        }

        request.getRequestDispatcher("/signIn").forward(request, response); // 로그인 실패 시 내부 포워딩
    }
}
