package ru.otus.hw05.service;


import ru.otus.hw05.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();

    Optional<Author> findById(long id);

    Author save(Author genre);

    long deleteById(long id);
}
