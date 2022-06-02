package com.alikhver.web.security.handler;

import com.alikhver.web.facade.OrganisationFacade;
import com.alikhver.web.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private OrganisationFacade organisationFacade;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        User user = (User) authentication.getPrincipal();
        String login = user.getUsername();
        String authority = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).get(0);

        String token = jwtTokenProvider.createToken(login, authority);

        Cookie authCookie = new Cookie("Authorization", token);
        authCookie.setPath("/");
        response.addCookie(authCookie);

        HttpSession session = request.getSession();

        String referer = (String) session.getAttribute("referer");
        String redirectTo = "/";

        if (Objects.isNull(referer)) {
            switch (authority) {
                case "REDACTOR":
                    Long organisationId = organisationFacade.getOrganisationIdByRedactorLogin(login);
                    redirectTo = "/organisation/" + organisationId;
                    break;
                case "ADMIN":
                    redirectTo = "/adminHome";
                    break;
                case "USER":
                    redirectTo = "/";
                    break;
            }
        } else {
            redirectTo = referer;
        }

        response.sendRedirect(redirectTo);
    }


}