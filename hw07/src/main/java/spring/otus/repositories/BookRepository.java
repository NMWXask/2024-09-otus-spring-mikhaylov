package spring.otus.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.otus.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository  extends JpaRepository<Book, Long>  {

    @EntityGraph(value = "book-author-genre-comments-entity-graph")
    Optional<Book> findWithCommentsById(long id);

    @EntityGraph(value = "book-author-genre-entity-graph")
    Optional<Book> findById(long id);

    @EntityGraph(value = "book-author-genre-entity-graph")
    List<Book> findAll();
    

}
