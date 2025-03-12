package spring.otus.hw11.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentUpdateDto(@NotNull(message = "Comment ID cannot be empty") Long id,
                               @NotBlank(message = "Comment cannot be empty") String text)  {

}