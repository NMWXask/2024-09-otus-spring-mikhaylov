package spring.otus.hw08.services;

import spring.otus.hw08.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();

    Optional<Author> findById(String id);

    Author save(Author genre);

    void deleteById(String id);
}
