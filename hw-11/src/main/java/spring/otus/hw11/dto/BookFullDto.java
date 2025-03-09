package spring.otus.hw11.dto;

public record BookFullDto(Long id,
                          String title,
                          String author,
                          String genre) {
}