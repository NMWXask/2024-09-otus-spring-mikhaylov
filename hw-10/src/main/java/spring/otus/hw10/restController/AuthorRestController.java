package spring.otus.hw10.restController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.otus.hw10.dto.AuthorDto;
import spring.otus.hw10.services.AuthorService;


import java.util.List;

@RequiredArgsConstructor
@RestController
public class AuthorRestController {

    private final AuthorService authorService;

    @GetMapping("/api/authors")
    public List<AuthorDto> getAllAuthors() {
        return authorService.findAll();
    }

}
