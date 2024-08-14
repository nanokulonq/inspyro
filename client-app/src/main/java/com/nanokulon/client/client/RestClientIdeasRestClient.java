package com.nanokulon.client.client;

import com.nanokulon.client.controller.payload.NewIdeaPayload;
import com.nanokulon.client.controller.payload.UpdateIdeaPayload;
import com.nanokulon.client.entity.Idea;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class RestClientIdeasRestClient implements IdeasRestClient {

    private static final ParameterizedTypeReference<List<Idea>> IDEA_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;

    @Override
    public List<Idea> findAllIdeas(String filter) {
        return this.restClient
                .get()
                .uri("/ideas-api/ideas?filter={filter}", filter)
                .retrieve()
                .body(IDEA_TYPE_REFERENCE);
    }

    @Override
    public Idea createIdea(String title, String description) {
        try {
            return this.restClient
                    .post()
                    .uri("/ideas-api/ideas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewIdeaPayload(title, description))
                    .retrieve()
                    .body(Idea.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<Idea> findIdea(int ideaId) {
        try {
            return Optional.ofNullable(this.restClient
                    .get()
                    .uri("/ideas-api/ideas/{ideaId}", ideaId)
                    .retrieve()
                    .body(Idea.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateIdea(int ideaId, String title, String description) {
        try {
            this.restClient
                    .patch()
                    .uri("/ideas-api/ideas/{ideaId}", ideaId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateIdeaPayload(title, description))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteIdea(int ideaId) {
        try {
            this.restClient
                    .delete()
                    .uri("/ideas-api/ideas/{ideaId}", ideaId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
