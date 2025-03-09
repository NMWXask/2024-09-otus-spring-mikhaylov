package spring.otus.hw10.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.otus.hw10.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
