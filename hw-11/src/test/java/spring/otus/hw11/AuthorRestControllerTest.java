package spring.otus.hw11;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import spring.otus.hw11.dto.AuthorDto;
import spring.otus.hw11.mapper.AuthorMapper;
import spring.otus.hw11.models.Author;
import spring.otus.hw11.repositories.AuthorRepository;
import spring.otus.hw11.restController.AuthorRestController;

import java.util.List;


@WebFluxTest(AuthorRestController.class)
class AuthorRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private AuthorMapper authorMapper;

    @Test
    void listShouldRenderAuthors() throws Exception {
        List<Author> authors = List.of(
                new Author(1L, "Author1"),
                new Author(2L, "Author2")
        );

        List<AuthorDto> authorDtos = List.of(
                new AuthorDto(1L, "Author1"),
                new AuthorDto(2L, "Author2")
        );

        Flux<Author> authorsFlux = Flux.fromIterable(authors);

        Mockito.when(authorRepository.findAll()).thenReturn(authorsFlux);
        Mockito.when(authorMapper.toDto(authors.get(0))).thenReturn(authorDtos.get(0));
        Mockito.when(authorMapper.toDto(authors.get(1))).thenReturn(authorDtos.get(1));

        webTestClient.get().uri("/api/authors")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorDto.class).isEqualTo(authorDtos);
    }

}
