package com.nanokulon.ideas.service;

import com.nanokulon.ideas.entity.Idea;
import com.nanokulon.ideas.repository.IdeaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultIdeaService implements IdeaService {

    private final IdeaRepository ideaRepository;

    @Override
    @Transactional(readOnly = true)
    public Iterable<Idea> findAllIdeas(String filter) {
        if (filter != null && !filter.isEmpty()) {
            return this.ideaRepository.findAllByTitleAndDescriptionLikeIgnoreCase("%" + filter + "%");
        } else {
            return this.ideaRepository.findAll();
        }
    }

    @Override
    @Transactional
    public Idea createIdea(String title, String description) {
        return this.ideaRepository.save(new Idea(null, title, description));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Idea> findIdea(int ideaId) {
        return this.ideaRepository.findById(ideaId);
    }

    @Override
    @Transactional
    public void updateIdea(Integer id, String title, String description) {
        this.ideaRepository.findById(id)
                .ifPresentOrElse(idea -> {
                    idea.setTitle(title);
                    idea.setDescription(description);
                }, () -> {
                    throw  new NoSuchElementException();
                });
    }

    @Override
    @Transactional
    public void deleteIdea(Integer id) {
        this.ideaRepository.deleteById(id);
    }
}
