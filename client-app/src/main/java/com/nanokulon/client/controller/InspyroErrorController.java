package com.nanokulon.client.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class InspyroErrorController implements ErrorController {

    private final MessageSource messageSource;

    @RequestMapping("/error")
    public String handleError(Model model, Locale locale,
                              NoResourceFoundException exception) {
        model.addAttribute("error",
                this.messageSource.getMessage(exception.getMessage(), new Object[0],
                        exception.getMessage(), locale));
        return "errors/404";
    }
}
