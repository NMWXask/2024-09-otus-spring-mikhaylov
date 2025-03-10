package ru.otus.hw05.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw05.converter.GenreConverter;
import ru.otus.hw05.model.Genre;
import ru.otus.hw05.service.GenreService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class GenreCommands {

    private final GenreService genreService;

    private final GenreConverter genreConverter;

    @ShellMethod(value = "Find all genres", key = "ag")
    public String findAllGenres() {
        return genreService.findAll().stream()
                .map(genreConverter::genreToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Delete genre", key = "dg")
    public long deleteGenre(long id) {
        return genreService.deleteById(id);
    }

    @ShellMethod(value = "Find genre by id", key = "fg")
    public String findById(long id) {
        return genreService.findById(id)
                .map(genreConverter::genreToString)
                .orElse(String.format("Genre with id = %s not found", id));
    }

    @ShellMethod(value = "Save genre", key = "sg")
    public String saveGenre(String name) {
        Genre genre = new Genre();
        genre.setName(name);
        return genreConverter.genreToString(genreService.save(genre));
    }

    @ShellMethod(value = "Update genre", key = "ug")
    public String updateGenre(long id, String name) {
        return genreConverter.genreToString(genreService.save(new Genre(id, name)));
    }
}
