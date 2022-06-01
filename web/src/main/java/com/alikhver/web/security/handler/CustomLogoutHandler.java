package com.alikhver.web.security.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@Configuration
public class CustomLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        HttpSession session = request.getSession();

        String referer = (String) session.getAttribute("referer");
        String redirectTo;

        if (Objects.isNull(referer)) {
            redirectTo = "/";
        } else {
            redirectTo = referer;
        }

        try {
            response.sendRedirect(redirectTo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
