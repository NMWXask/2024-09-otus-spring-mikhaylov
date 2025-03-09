package spring.otus.hw08.services;


import spring.otus.hw08.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<Genre> findAll();

    Optional<Genre> findById(String id);

    Genre save(Genre genre);

    void deleteById(String id);
}
