package com.alikhver.web.controller;

import com.alikhver.web.exception.CustomLocalizedException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
@RequiredArgsConstructor
@Validated
public class HomeController {

    @GetMapping("/")
    @ApiOperation("View Home")
    public ModelAndView viewHome(ModelAndView modelAndView) {

        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping("/adminHome")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation("View Home")
    public ModelAndView viewAdminHome(ModelAndView modelAndView) {

        modelAndView.setViewName("home/adminHome");

        return modelAndView;
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
