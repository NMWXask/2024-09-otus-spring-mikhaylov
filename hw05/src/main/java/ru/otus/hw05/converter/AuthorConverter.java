package ru.otus.hw05.converter;

import org.springframework.stereotype.Component;
import ru.otus.hw05.model.Author;

@Component
public class AuthorConverter {
    public String authorToString(Author author) {
        return "Id: %d, FullName: %s".formatted(author.getId(), author.getName());
    }
}
