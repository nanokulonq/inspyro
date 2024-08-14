package com.nanokulon.client.controller;

import com.nanokulon.client.client.BadRequestException;
import com.nanokulon.client.client.IdeasRestClient;
import com.nanokulon.client.controller.payload.NewIdeaPayload;
import com.nanokulon.client.entity.Idea;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ideas")
public class IdeasController {

    private final IdeasRestClient ideasRestClient;

    @GetMapping
    public String getIdeasList(Model model, @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("ideas", this.ideasRestClient.findAllIdeas(filter));
        model.addAttribute("filter", filter);
        return "ideas";
    }

    @GetMapping("create")
    public String getNewIdeaPage() {
        return "new_idea";
    }

    @PostMapping("create")
    public String createIdea(NewIdeaPayload payload, Model model) {
        try {
            Idea idea = this.ideasRestClient.createIdea(payload.title(), payload.description());
            return "redirect:/ideas/%d".formatted(idea.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "new_idea";
        }
    }
}
