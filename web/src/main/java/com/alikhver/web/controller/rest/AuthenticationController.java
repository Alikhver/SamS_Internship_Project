package com.alikhver.web.controller.rest;

import com.alikhver.web.dto.authentication.AuthenticationRequest;
import com.alikhver.web.dto.user.response.GetUserResponse;
import com.alikhver.web.facade.OrganisationFacade;
import com.alikhver.web.facade.UserFacade;
import com.alikhver.web.security.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final OrganisationFacade organisationFacade;

    private final AuthenticationManager authenticationManager;
    private final UserFacade userFacade;
    private final JwtTokenProvider jwtTokenProvider;

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

    @PostMapping("/authenticate")
    @ApiOperation("Authenticate user with Rest")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> authenticate(HttpServletResponse httpServletResponse,
                                          @RequestBody AuthenticationRequest authInfo) {
        try {
//            TODO user not found not correct password
//            TODO method in facade
            GetUserResponse user = userFacade.findByLogin(authInfo.getLogin());
            var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authInfo.getLogin(), authInfo.getPassword()));
            String token = jwtTokenProvider.createToken(authInfo.getLogin(), user.getRole());
            Cookie authCookie = new Cookie("Authorization", token);
            authCookie.setPath("/");
            httpServletResponse.addCookie(authCookie);

            Map<Object, Object> responseBody = new HashMap<>();
            responseBody.put("jwt", token);
            return ResponseEntity.ok(responseBody);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid login/password combination", HttpStatus.FORBIDDEN);
        }
    }
}
