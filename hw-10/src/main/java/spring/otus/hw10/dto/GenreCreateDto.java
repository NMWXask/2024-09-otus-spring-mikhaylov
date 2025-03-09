package spring.otus.hw10.dto;

import jakarta.validation.constraints.NotBlank;

public record GenreCreateDto(@NotBlank(message = "Name cannot be empty") String name)  {
}