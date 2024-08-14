package com.nanokulon.client.controller;

import com.nanokulon.client.client.BadRequestException;
import com.nanokulon.client.client.IdeasRestClient;
import com.nanokulon.client.controller.payload.UpdateIdeaPayload;
import com.nanokulon.client.entity.Idea;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/ideas/{ideaId:\\d+}")
@RequiredArgsConstructor
public class IdeaController {

    private final IdeasRestClient ideasRestClient;

    private final MessageSource messageSource;

    @ModelAttribute("idea")
    public Idea idea(@PathVariable("ideaId") int ideaId) {
        return this.ideasRestClient.findIdea(ideaId)
                .orElseThrow(() -> new NoSuchElementException("ideas.errors.idea_not_found"));
    }

    @GetMapping
    public String getIdea() {
        return "idea";
    }

    @GetMapping("edit")
    public String getIdeaEditPage() {
        return "edit";
    }

    @PostMapping("edit")
    public String updateIdea(@ModelAttribute(value = "idea", binding = false) Idea idea,
                             UpdateIdeaPayload payload, Model model) {
        try {
            this.ideasRestClient.updateIdea(idea.id(), payload.title(), payload.description());
            return "redirect:/ideas/%d".formatted(idea.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "edit";
        }
    }

    @PostMapping("delete")
    public String deleteIdea(@ModelAttribute("idea") Idea idea) {
        this.ideasRestClient.deleteIdea(idea.id());
        return "redirect:/ideas";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model,
                                               HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                this.messageSource.getMessage(exception.getMessage(), new Object[0],
                        exception.getMessage(), locale));
        return "errors/404";
    }
}
