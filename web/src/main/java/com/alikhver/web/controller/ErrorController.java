package com.alikhver.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    @GetMapping("/error123")
    public String handleError(HttpServletRequest request) {
//        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//
//        if (status != null) {
//            int statusCode = Integer.parseInt(status.toString());
//
//            if(statusCode == HttpStatus.NOT_FOUND.value()) {
//                return "error/error-500";
//            }
//            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//                return "error/error-500";
//            }
//        }
//        return "error/error-500";
        return "/error/error-404";
    }
}
