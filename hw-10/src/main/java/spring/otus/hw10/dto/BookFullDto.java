package spring.otus.hw10.dto;

public record BookFullDto(Long id,
                          String title,
                          String author,
                          String genre) {
}