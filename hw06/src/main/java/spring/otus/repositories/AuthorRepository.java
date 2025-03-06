package spring.otus.repositories;


import spring.otus.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> findAll();

    Optional<Author> findById(long id);

    Author save(Author author);

    void deleteById(long id);
}
