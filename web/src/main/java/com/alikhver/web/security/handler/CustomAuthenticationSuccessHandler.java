package com.alikhver.web.security.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
//        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
//
//        if (roles.contains("ADMIN")) {
//            response.sendRedirect("/admin/home");
//        } else if (roles.contains("DOCTOR")) {
//            response.sendRedirect("/doctor/home");
//        }
//        else {
//            response.sendRedirect("/user/home");
//        }
        response.sendRedirect("/organisation/1");
    }
}