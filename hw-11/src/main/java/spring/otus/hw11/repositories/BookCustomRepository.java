package spring.otus.hw11.repositories;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.otus.hw11.models.Book;

public interface BookCustomRepository {

    Flux<Book> findAllBooks();

    Mono<Book> findByBookId(Long id);

    Mono<Book> saveBook(Book book);
}
