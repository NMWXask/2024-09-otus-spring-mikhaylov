package spring.otus.hw11;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import spring.otus.hw11.dto.GenreDto;
import spring.otus.hw11.mapper.GenreMapper;
import spring.otus.hw11.models.Genre;
import spring.otus.hw11.repositories.GenreRepository;
import spring.otus.hw11.restController.GenreRestController;

import java.util.List;


@WebFluxTest(GenreRestController.class)
class GenreRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private GenreMapper genreMapper;

    @Test
    void listShouldRenderGenres() throws Exception {
        List<Genre> genres = List.of(new Genre(1L, "Genre1"), new Genre(2L, "Genre2"));
        List<GenreDto> genreDtos = List.of(new GenreDto(1L, "Genre1"), new GenreDto(2L, "Genre2"));

        Flux<Genre> genresFlux = Flux.fromIterable(genres);

        Mockito.when(genreRepository.findAll()).thenReturn(genresFlux);
        Mockito.when(genreMapper.toDto(genres.get(0))).thenReturn(genreDtos.get(0));
        Mockito.when(genreMapper.toDto(genres.get(1))).thenReturn(genreDtos.get(1));

        webTestClient.get().uri("/api/genres")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GenreDto.class).isEqualTo(genreDtos);
    }
}