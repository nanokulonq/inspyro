package com.nanokulon.ideas.service;

import com.nanokulon.ideas.entity.Idea;

import java.util.Optional;

public interface IdeaService {

    Iterable<Idea> findAllIdeas(String filter);

    Idea createIdea(String title, String description);

    Optional<Idea> findIdea(int ideaId);

    void updateIdea(Integer id, String title, String description);

    void deleteIdea(Integer id);
}
