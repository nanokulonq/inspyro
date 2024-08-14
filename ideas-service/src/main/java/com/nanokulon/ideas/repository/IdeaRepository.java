package com.nanokulon.ideas.repository;

import com.nanokulon.ideas.entity.Idea;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IdeaRepository extends CrudRepository<Idea, Integer> {

    @Query(value = "select id, c_title, c_description from ideas.t_idea where c_title ilike :filter " +
            "or t_idea.c_description ilike :filter", nativeQuery = true)
    Iterable<Idea> findAllByTitleAndDescriptionLikeIgnoreCase(@Param("filter") String filter);
}
