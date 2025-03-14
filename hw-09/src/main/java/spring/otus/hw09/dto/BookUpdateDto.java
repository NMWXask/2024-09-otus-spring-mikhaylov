package spring.otus.hw09.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookUpdateDto(@NotNull(message = "Id cannot be empty") Long id,
                            @NotBlank(message = "Title cannot be empty") String title,
                            @NotNull(message = "Author is required") Long authorId,
                            @NotNull(message = "Genre is required") Long genreId) {
}