package spring.otus.hw08.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import spring.otus.hw08.models.Book;

import java.util.List;

public interface BookRepository  extends MongoRepository<Book, String> {

    List<Book> findBooksByAuthorId(String authorId);

    long countBooksByGenreId(String genreId);
}
