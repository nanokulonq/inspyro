package com.nanokulon.ideas.controller;

import com.nanokulon.ideas.controller.payload.NewIdeaPayload;
import com.nanokulon.ideas.entity.Idea;
import com.nanokulon.ideas.service.IdeaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("ideas-api/ideas")
public class IdeasRestController {

    private final IdeaService ideaService;

    @GetMapping
    public Iterable<Idea> findIdeas(@RequestParam(name = "filter", required = false) String filter) {
        return this.ideaService.findAllIdeas(filter);
    }

    @PostMapping
    public ResponseEntity<?> createIdea(@Valid @RequestBody NewIdeaPayload payload,
                                        BindingResult bindingResult,
                                        UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Idea idea = this.ideaService.createIdea(payload.title(), payload.description());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/ideas-api/ideas/{ideaId}")
                            .build(Map.of("ideaId", idea.getId())))
                    .body(idea);
        }
    }
}
