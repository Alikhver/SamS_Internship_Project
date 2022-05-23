package com.alikhver.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "security/login";
    }

    @GetMapping("/register")
    @ApiOperation("View register page")
    public String getRegisterPage() {return "security/registration";}
}
