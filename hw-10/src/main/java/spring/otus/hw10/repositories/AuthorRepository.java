package spring.otus.hw10.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.otus.hw10.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
