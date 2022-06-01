package com.alikhver.web.controller.rest;

import com.alikhver.web.facade.OrganisationFacade;
import com.alikhver.web.facade.UserFacade;
import com.alikhver.web.security.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final OrganisationFacade organisationFacade;

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @GetMapping("/forwardPathname")
    @ApiOperation("Get redirect Pathname after Login or Registration")
    public String getLoginForwardPathname() {
        String pathname;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String authority = new ArrayList<>(authentication.getAuthorities()).get(0).getAuthority();

        if (authority.equals("REDACTOR")) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            long organisationId = organisationFacade.getOrganisationIdByRedactorLogin(userDetails.getUsername());
            pathname = "/organisation/" + organisationId;
        } else if (authority.equals("ADMIN")) {
            pathname = "/home";
        } else {
            pathname = "/";
        }

        return pathname;
    }
}
