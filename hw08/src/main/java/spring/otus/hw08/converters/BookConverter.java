package spring.otus.hw08.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.otus.hw08.models.Book;

@RequiredArgsConstructor
@Component
public class BookConverter {
    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;

    private final CommentConverter commentConverter;

    public String bookToString(Book book) {
        return "Id: %s, title: %s, author: {%s}, genres: {%s}".formatted(
                book.getId(),
                book.getTitle(),
                authorConverter.authorToString(book.getAuthor()),
                genreConverter.genreToString(book.getGenre()));
    }

}
