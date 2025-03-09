package spring.otus.hw09.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorDto(Long id, @NotBlank(message = "Title cannot be empty") String name) {
}