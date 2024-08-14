package com.nanokulon.ideas.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewIdeaPayload(

        @NotNull(message = "{ideas.create.errors.title_is_null}")
        @Size(min = 3, max = 50, message = "{ideas.create.errors.title_size_is_invalid}")
        String title,

        @NotNull(message = "{ideas.create.errors.description_is_null}")
        @Size(min = 3, max = 500, message = "{ideas.create.errors.description_size_is_invalid}")
        String description) {
}
