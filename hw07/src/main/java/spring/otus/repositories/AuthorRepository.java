package spring.otus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.otus.models.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    
    List<Author> findAll();

}
