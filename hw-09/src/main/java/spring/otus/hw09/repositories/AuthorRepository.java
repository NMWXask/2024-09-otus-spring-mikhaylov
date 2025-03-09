package spring.otus.hw09.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.otus.hw09.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
