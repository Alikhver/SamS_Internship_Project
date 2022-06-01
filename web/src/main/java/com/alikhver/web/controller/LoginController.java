package com.alikhver.web.controller;

import com.alikhver.web.dto.authentication.AuthenticationRequest;
import com.alikhver.web.dto.user.response.GetUserResponse;
import com.alikhver.web.facade.UserFacade;
import com.alikhver.web.security.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Value("${hostName}")
    private String hostName;

    private final AuthenticationManager authenticationManager;
    private final UserFacade userFacade;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginController(AuthenticationManager authenticationManager, UserFacade userFacade, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userFacade = userFacade;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage(ModelAndView modelAndView, HttpServletRequest request, HttpSession session) throws MalformedURLException {

        try {
            URL url = new URL(request.getHeader("Referer"));
            String host = url.getHost();
            String path = url.getPath();
            if (host.equals(hostName) && path.equals("/profile/")) {

            } else if (host.equals(hostName)) {
                session.setAttribute("referer", url.toString());
            } else {
                session.removeAttribute("referer");
            }
        } catch (MalformedURLException e) {
            session.removeAttribute("referer");
        }

        modelAndView.addObject("authRequest", new AuthenticationRequest());
        modelAndView.setViewName("security/login");
        return modelAndView;
    }

    @GetMapping("/register")
    @ApiOperation("View register page")
    public String getRegisterPage() {
        return "security/registration";
    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(HttpServletResponse httpServletResponse,
                                          @ModelAttribute AuthenticationRequest authInfo) {
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
