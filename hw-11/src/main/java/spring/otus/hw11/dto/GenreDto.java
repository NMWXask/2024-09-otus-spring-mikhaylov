package spring.otus.hw11.dto;

import jakarta.validation.constraints.NotBlank;

public record GenreDto(Long id, @NotBlank(message = "Name cannot be empty") String name)  {
}