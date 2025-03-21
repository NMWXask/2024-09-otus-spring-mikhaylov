package spring.otus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.otus.models.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    
    List<Genre> findAll();

}
