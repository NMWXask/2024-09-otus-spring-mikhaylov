package spring.otus.hw08.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.otus.hw08.models.Genre;
import spring.otus.hw08.repositories.GenreRepository;
import spring.otus.hw08.services.GenreServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreServiceImpl genreService;

    private Genre genre1;
    private Genre genre2;

    @BeforeEach
    void setUp() {
        genre1 = new Genre("1", "Fiction");
        genre2 = new Genre("2", "Non-Fiction");
    }

    @Test
    void testFindAllWhenGenresExistThenReturnGenres() {
        List<Genre> genres = Arrays.asList(genre1, genre2);
        when(genreRepository.findAll()).thenReturn(genres);

        List<Genre> result = genreService.findAll();

        verify(genreRepository).findAll();
        assertThat(result).isEqualTo(genres);
    }

    @Test
    void testFindByIdWhenGenreExistsThenReturnGenre() {
        String genreId = "1";
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre1));

        Optional<Genre> result = genreService.findById(genreId);

        verify(genreRepository).findById(genreId);
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(genre1);
    }

    @Test
    void testSaveWhenGenreIsValidThenSaveGenre() {
        when(genreRepository.save(genre1)).thenReturn(genre1);

        Genre result = genreService.save(genre1);

        verify(genreRepository).save(genre1);
        assertThat(result).isEqualTo(genre1);
    }

    @Test
    void testDeleteByIdWhenGenreExistsThenDeleteGenre() {
        String genreId = "1";

        genreService.deleteById(genreId);

        verify(genreRepository).deleteById(genreId);
    }
}
