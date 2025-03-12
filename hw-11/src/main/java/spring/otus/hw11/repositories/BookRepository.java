package spring.otus.hw11.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import spring.otus.hw11.models.Book;

public interface BookRepository  extends ReactiveCrudRepository<Book, Long>, BookCustomRepository {
}
