package ru.otus.hw05.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw05.converter.AuthorConverter;
import ru.otus.hw05.model.Author;
import ru.otus.hw05.service.AuthorService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class AuthorCommands {

    private final AuthorService authorService;

    private final AuthorConverter authorConverter;

    @ShellMethod(value = "Find all authors", key = "aa")
    public String findAllAuthors() {
        return authorService.findAll().stream()
                .map(authorConverter::authorToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Delete author", key = "da")
    public long deleteGenre(long id) {
        return authorService.deleteById(id);
    }

    @ShellMethod(value = "Find author by id", key = "fa")
    public String findById(long id) {
        return authorService.findById(id)
                .map(authorConverter::authorToString)
                .orElse(String.format("Author with id = %s not found", id));
    }

    @ShellMethod(value = "Save author", key = "sa")
    public String saveGenre(String name) {
        Author author = new Author();
        author.setName(name);
        return authorConverter.authorToString(authorService.save(author));
    }

    @ShellMethod(value = "Update genre", key = "ua")
    public String updateGenre(long id, String name) {
        return authorConverter.authorToString(authorService.save(new Author(id, name)));
    }
}
