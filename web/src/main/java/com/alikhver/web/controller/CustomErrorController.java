package com.alikhver.web.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest response) {
        Object statusCode = response.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (statusCode != null) {
            HttpStatus status = HttpStatus.resolve(Integer.parseInt(statusCode.toString()));

            if (status == HttpStatus.NOT_FOUND) {
                return "error/404";
            } else if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
                return "error/500";
            } else if (status == HttpStatus.BAD_REQUEST) {
                return "error/400";
            } else if (status == HttpStatus.FORBIDDEN) {
                return "error/403";
            }
        }

        return "error/404";
    }

}
