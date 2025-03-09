package spring.otus.hw11.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import spring.otus.hw11.dto.AuthorDto;
import spring.otus.hw11.mapper.AuthorMapper;
import spring.otus.hw11.repositories.AuthorRepository;

@RequiredArgsConstructor
@RestController
public class AuthorRestController {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;


    @GetMapping("/api/authors")
    public Flux<AuthorDto> getAllAuthors() {
        return authorRepository.findAll()
                .map(authorMapper::toDto);
    }
}
