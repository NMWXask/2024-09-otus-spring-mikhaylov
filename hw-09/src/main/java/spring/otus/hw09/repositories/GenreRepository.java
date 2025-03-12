package spring.otus.hw09.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.otus.hw09.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
