package spring.otus.hw09.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record CommentCreateDto(@NotNull(message = "Book is required") Long bookId,
                               @NotBlank(message = "Comment cannot be empty") String text)  {
}