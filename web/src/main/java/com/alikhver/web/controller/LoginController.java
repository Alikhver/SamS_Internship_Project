package com.alikhver.web.controller;

import com.alikhver.web.dto.authentication.AuthenticationRequest;
import com.alikhver.web.exception.CustomLocalizedException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

@Controller
public class LoginController {

    @Value("${hostName}")
    private String hostName;

    @GetMapping("/login")
    @ApiOperation(("View Login Page"))
    public ModelAndView getLoginPage(@RequestParam(required = false) String error,
                                     ModelAndView modelAndView,
                                     HttpServletRequest request, HttpSession session) {
        try {

            URL url = new URL(request.getHeader("Referer"));
            String host = url.getHost();
            String path = url.getPath();

            if (host.equals(hostName) &&
                    !path.equals("/profile/current") &&
                    !path.equals("/profile/update") &&
                    !path.equals("/register") &&
                    !path.equals("/profile/records") &&
                    !path.equals("/login")
            ) {
                session.setAttribute("referer", url.toString());
            } else if (!host.equals(hostName)) {
                session.setAttribute("referer", "/");
            }

        } catch (MalformedURLException e) {
            session.setAttribute("referer", "/");
        }

        if (error != null) {
            modelAndView.addObject("wrongCredentials", true);
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


    @ExceptionHandler(CustomLocalizedException.class)
    public ModelAndView handleNoOrganisationFoundException(CustomLocalizedException e) {
        ModelAndView modelAndView = new ModelAndView("error/customError");


        Locale locale = LocaleContextHolder.getLocale();

        modelAndView.addObject("msg", e.getLocalizedMessage(locale));
        modelAndView.addObject("status", e.status);

        return modelAndView;
    }
}
