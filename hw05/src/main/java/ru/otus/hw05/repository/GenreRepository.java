package ru.otus.hw05.repository;


import ru.otus.hw05.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> findAll();

    Optional<Genre> findById(long id);

    Genre save(Genre genre);

    long deleteById(long id);
}
