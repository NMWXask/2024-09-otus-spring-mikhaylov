package ru.otus.hw05.converter;

import org.springframework.stereotype.Component;
import ru.otus.hw05.model.Genre;

@Component
public class GenreConverter {
    public String genreToString(Genre genre) {
        return "Id: %d, Name: %s".formatted(genre.getId(), genre.getName());
    }
}
