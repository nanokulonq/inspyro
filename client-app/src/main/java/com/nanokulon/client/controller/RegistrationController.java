package com.nanokulon.client.controller;

import com.nanokulon.client.controller.payload.NewUserPayload;
import com.nanokulon.client.entity.InspyroUser;
import com.nanokulon.client.repository.InspyroUserRepository;
import com.nanokulon.client.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {
    private final RegistrationService registrationService;

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String registerUser(NewUserPayload payload, Errors errors,
                               Model model) {
        try {
            this.registrationService.register(payload);
            return "redirect:/";
        } catch (DataIntegrityViolationException exception) {
            model.addAttribute("errors", exception.getLocalizedMessage());
            return "registration";
        }
    }

}
