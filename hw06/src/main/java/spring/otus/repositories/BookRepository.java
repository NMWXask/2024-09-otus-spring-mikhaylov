package spring.otus.repositories;


import spring.otus.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(long id);

    Optional<Book> findWithCommentsById(long id);

    List<Book> findAll();

    Book save(Book book);

    void deleteById(long id);
}
