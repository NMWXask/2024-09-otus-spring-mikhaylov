package spring.otus.hw08.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import spring.otus.hw08.models.Author;
import spring.otus.hw08.models.Book;
import spring.otus.hw08.repositories.AuthorRepository;
import spring.otus.hw08.repositories.BookRepository;
import spring.otus.hw08.services.AuthorServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @BeforeEach
    void setUp() {
        reset(authorRepository, bookRepository);
    }

    @Test
    void testFindAllWhenAuthorsExistThenReturnListOfAuthors() {
        List<Author> authors = Arrays.asList(new Author("1", "Author1"), new Author("2", "Author2"));
        when(authorRepository.findAll()).thenReturn(authors);

        List<Author> result = authorService.findAll();

        assertThat(result).isEqualTo(authors);
    }

    @Test
    void testFindByIdWhenAuthorExistsThenReturnAuthor() {
        String authorId = "1";
        Author author = new Author(authorId, "Author1");
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        Optional<Author> result = authorService.findById(authorId);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    void testSaveWhenAuthorIsGivenThenSaveAuthor() {
        Author author = new Author("1", "Author1");
        when(authorRepository.save(author)).thenReturn(author);

        Author result = authorService.save(author);

        assertThat(result).isEqualTo(author);
    }

    @Test
    @Transactional
    void testDeleteByIdWhenAuthorExistsThenDeleteAuthorAndBooks() {
        String authorId = "1";
        List<Book> books = Arrays.asList(new Book("1", "Book1", new Author(authorId, "Author1"), null));
        when(bookRepository.findBooksByAuthorId(authorId)).thenReturn(books);

        authorService.deleteById(authorId);

        verify(authorRepository).deleteById(authorId);
        verify(bookRepository).findBooksByAuthorId(authorId);
        for (Book book : books) {
            verify(bookRepository).deleteById(book.getId());
        }
    }
}