package spring.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.otus.models.Author;
import spring.otus.repositories.AuthorRepository;
import spring.otus.services.AuthorServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private List<Author> authors;

    @BeforeEach
    void setUp() {
        authors = Arrays.asList(
                new Author(1L, "Author One"),
                new Author(2L, "Author Two")
        );
    }

    @Test
    void testFindAllWhenAuthorsExistThenReturnAuthors() {
        when(authorRepository.findAll()).thenReturn(authors);

        List<Author> result = authorService.findAll();

        assertThat(result).isEqualTo(authors);
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdWhenAuthorExistsThenReturnAuthor() {
        Author author = new Author(1L, "Author One");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Optional<Author> result = authorService.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdWhenAuthorDoesNotExistThenReturnEmptyOptional() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Author> result = authorService.findById(1L);

        assertThat(result).isNotPresent();
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveWhenAuthorIsGivenThenSaveAndReturnAuthor() {
        Author author = new Author(1L, "Author One");
        when(authorRepository.save(author)).thenReturn(author);

        Author result = authorService.save(author);

        assertThat(result).isEqualTo(author);
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testDeleteByIdWhenIdIsGivenThenDeleteAuthor() {
        long authorId = 1L;
        doNothing().when(authorRepository).deleteById(authorId);

        authorService.deleteById(authorId);

        verify(authorRepository, times(1)).deleteById(authorId);
    }
}
