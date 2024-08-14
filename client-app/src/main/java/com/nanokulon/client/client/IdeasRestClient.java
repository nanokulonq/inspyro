package com.nanokulon.client.client;

import com.nanokulon.client.entity.Idea;

import java.util.List;
import java.util.Optional;

public interface IdeasRestClient {

    List<Idea> findAllIdeas(String filter);

    Idea createIdea(String title, String description);

    Optional<Idea> findIdea(int ideaId);

    void updateIdea(int ideaId, String title, String description);

    void deleteIdea(int ideaId);
}
