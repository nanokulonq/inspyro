package com.nanokulon.client.controller;

import com.nanokulon.client.client.BadRequestException;
import com.nanokulon.client.client.IdeasRestClient;
import com.nanokulon.client.controller.payload.NewIdeaPayload;
import com.nanokulon.client.entity.Idea;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Модульные тесты IdeasController")
class IdeasControllerTest {

    @Mock
    IdeasRestClient ideasRestClient;

    @InjectMocks
    IdeasController controller;

    @Test
    @DisplayName("createIdea создаст новую идею и перенаправит на страницу этой идеи")
    void createIdea_RequestIsValid_ReturnsRedirectionToIdeaPage() {
        // given
        var payload = new NewIdeaPayload("Новая идея", "Описание идеи");
        var model = new ConcurrentModel();

        doReturn(new Idea(1, "Новая идея", "Описание идеи"))
                .when(this.ideasRestClient)
                .createIdea("Новая идея", "Описание идеи");

        // when
         var result = this.controller.createIdea(payload, model);

        // then
        assertEquals("redirect:/ideas/1", result);

        verify(this.ideasRestClient).createIdea("Новая идея", "Описание идеи");
        verifyNoMoreInteractions(this.ideasRestClient);
    }

    @Test
    @DisplayName("createIdea вернёт страницу с ошибками, если запрос невалиден")
    void createIdea_RequestIsInValid_ReturnsIdeaFormWithErrors() {
        // given
        var payload = new NewIdeaPayload(" ", null);
        var model = new ConcurrentModel();

        doThrow(new BadRequestException(List.of("Ошибка 1", "Ошибка 2")))
                .when(this.ideasRestClient)
                .createIdea(" ", null);

        // when
        var result = this.controller.createIdea(payload, model);

        // then
        assertEquals("new_idea", result);
        assertEquals(payload, model.getAttribute("payload"));
        assertEquals(List.of("Ошибка 1", "Ошибка 2"), model.getAttribute("errors"));

        verify(this.ideasRestClient).createIdea(" ", null);
        verifyNoMoreInteractions(this.ideasRestClient);
    }
}