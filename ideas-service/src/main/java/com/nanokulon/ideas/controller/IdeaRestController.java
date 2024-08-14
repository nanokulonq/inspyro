package com.nanokulon.ideas.controller;

import com.nanokulon.ideas.controller.payload.UpdateIdeaPayload;
import com.nanokulon.ideas.entity.Idea;
import com.nanokulon.ideas.service.IdeaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("ideas-api/ideas/{ideaId:\\d+}")
public class IdeaRestController {

    private final IdeaService ideaService;

    private final MessageSource messageSource;

    @ModelAttribute
    public Idea getIdea(@PathVariable("ideaId") int ideaId) {
        return this.ideaService.findIdea(ideaId)
                .orElseThrow(() -> new NoSuchElementException("ideas.errors.idea_not_found"));
    }

    @GetMapping
    public Idea findIdea(@ModelAttribute("idea") Idea idea) {
        return idea;
    }

    @PatchMapping
    public ResponseEntity<?> updateIdea(@PathVariable("ideaId") int ideaId,
                                           @Valid @RequestBody UpdateIdeaPayload payload,
                                           BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            this.ideaService.updateIdea(ideaId, payload.title(), payload.description());
            return ResponseEntity.noContent()
                    .build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteIdea(@PathVariable("ideaId") int ideaId) {
        this.ideaService.deleteIdea(ideaId);
        return ResponseEntity.noContent()
                .build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception,
                                                                      Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        this.messageSource.getMessage(exception.getMessage(), new Object[0],
                                exception.getMessage(), locale)));
    }
}
