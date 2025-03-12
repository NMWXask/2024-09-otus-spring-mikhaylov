package spring.otus.hw08.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import spring.otus.hw08.models.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
}
