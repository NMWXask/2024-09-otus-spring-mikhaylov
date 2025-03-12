package spring.otus.hw11.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import spring.otus.hw11.dto.GenreDto;
import spring.otus.hw11.mapper.GenreMapper;
import spring.otus.hw11.repositories.GenreRepository;

@RequiredArgsConstructor
@RestController
public class GenreRestController {

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    @GetMapping("/api/genres")
    public Flux<GenreDto> getAllGenres() {
        return  genreRepository.findAll()
                .map(genreMapper::toDto);

    }
}
