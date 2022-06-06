package com.alikhver.web.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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
}
