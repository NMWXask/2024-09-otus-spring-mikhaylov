package spring.otus.hw11.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import spring.otus.hw11.models.Genre;

public interface GenreRepository extends ReactiveCrudRepository<Genre, Long> {
}
