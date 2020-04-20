package com.whuamps.component;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//自定义的登录成功操作器
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //System.out.println(httpServletRequest.getMethod().toString());
        httpServletRequest.getSession().setAttribute("loginUser",authentication.getName());
        httpServletRequest.getRequestDispatcher("/toAuto").forward(httpServletRequest,httpServletResponse);
    }
}
