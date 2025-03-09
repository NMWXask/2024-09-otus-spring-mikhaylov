package spring.otus.converters;

import org.springframework.stereotype.Component;
import spring.otus.models.Author;

@Component
public class AuthorConverter {
    public String authorToString(Author author) {
        return "Id: %d, FullName: %s".formatted(author.getId(), author.getName());
    }
}
