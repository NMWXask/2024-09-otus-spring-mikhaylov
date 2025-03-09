package spring.otus.hw10.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorCreateDto(@NotBlank(message = "Title cannot be empty") String name) {
}