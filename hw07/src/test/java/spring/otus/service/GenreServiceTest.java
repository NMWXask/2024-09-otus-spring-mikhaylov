package spring.otus.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.otus.models.Genre;
import spring.otus.repositories.GenreRepository;
import spring.otus.services.GenreServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreServiceImpl genreService;

    @Test
    public void testFindAllWhenGenresExistThenReturnGenres() {
        Genre genre1 = new Genre(1L, "Fiction");
        Genre genre2 = new Genre(2L, "Non-Fiction");
        List<Genre> expectedGenres = Arrays.asList(genre1, genre2);
        when(genreRepository.findAll()).thenReturn(expectedGenres);

        List<Genre> actualGenres = genreService.findAll();

        assertEquals(expectedGenres, actualGenres);
    }

    @Test
    public void testFindByIdWhenGenreExistsThenReturnGenre() {
        Genre expectedGenre = new Genre(1L, "Fiction");
        when(genreRepository.findById(1L)).thenReturn(Optional.of(expectedGenre));

        Optional<Genre> actualGenre = genreService.findById(1L);

        assertTrue(actualGenre.isPresent());
        assertEquals(expectedGenre, actualGenre.get());
    }

    @Test
    public void testFindByIdWhenGenreDoesNotExistThenReturnEmptyOptional() {
        when(genreRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Genre> actualGenre = genreService.findById(1L);

        assertFalse(actualGenre.isPresent());
    }

    @Test
    public void testSaveWhenGenreIsValidThenReturnSavedGenre() {
        Genre genreToSave = new Genre(null, "Fiction");
        Genre expectedSavedGenre = new Genre(1L, "Fiction");
        when(genreRepository.save(genreToSave)).thenReturn(expectedSavedGenre);

        Genre actualSavedGenre = genreService.save(genreToSave);

        assertEquals(expectedSavedGenre, actualSavedGenre);
    }

    @Test
    public void testDeleteByIdWhenGenreExistsThenDeleteGenre() {
        long genreId = 1L;
        doNothing().when(genreRepository).deleteById(genreId);

        genreService.deleteById(genreId);

        verify(genreRepository, times(1)).deleteById(genreId);
    }
}