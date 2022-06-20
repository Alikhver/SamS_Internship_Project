package com.alikhver.web.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

//    @Override
//    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
//
//            if (status == HttpStatus.NOT_FOUND) {
//                return new ModelAndView("404");
//            } else if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
//                return new ModelAndView("500");
//            }
//
//        return null;
//    }

//    @RequestMapping("/404")
//    public String handleError(HttpServletRequest response, HttpServletRequest request) {
//
//
//        return "error/404";
//    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest response) {
        Object status = response.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/500";
            }
        }

        return "error/404";
    }

}
