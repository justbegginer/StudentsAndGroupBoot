package org.student.site.StudentsAndGroupBoot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @RequestMapping("/error")
    public String handleErrors(HttpServletRequest httpServletRequest){
        Object status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "errors/error404";
            }
            else if(statusCode == HttpStatus.UNAUTHORIZED.value()){
                return "errors/error401";
            }
            else if(statusCode == HttpStatus.FORBIDDEN.value()){
                return "errors/error403";
            }
            else {
                System.out.println(statusCode);
                return null;
            }
        }
        return "error";
    }
}
