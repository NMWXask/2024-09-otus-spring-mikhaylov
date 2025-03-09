package spring.otus.hw08.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import spring.otus.hw08.models.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
