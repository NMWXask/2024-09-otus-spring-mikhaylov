package spring.otus.services;


import spring.otus.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();

    Optional<Author> findById(long id);

    Author save(Author genre);

    void deleteById(long id);
}
