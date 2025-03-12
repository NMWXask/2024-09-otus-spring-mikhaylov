package spring.otus.hw11.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookCreateDto(@NotBlank(message = "Title cannot be empty") String title,
                            @NotNull(message = "Author is required") Long authorId,
                            @NotNull(message = "Genre is required") Long genreId) {
}