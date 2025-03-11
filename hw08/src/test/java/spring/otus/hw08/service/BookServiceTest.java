package spring.otus.hw08.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.otus.hw08.exceptions.EntityNotFoundException;
import spring.otus.hw08.models.Author;
import spring.otus.hw08.models.Book;
import spring.otus.hw08.models.Genre;
import spring.otus.hw08.repositories.AuthorRepository;
import spring.otus.hw08.repositories.BookRepository;
import spring.otus.hw08.repositories.CommentRepository;
import spring.otus.hw08.repositories.GenreRepository;
import spring.otus.hw08.services.BookServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private Author author;
    private Genre genre;

    @BeforeEach
    void setUp() {
        author = new Author("1", "Author Name");
        genre = new Genre("1", "Genre Name");
        book = new Book("1", "Book Title", author, genre);
    }

    @Test
    void testFindByIdWhenBookExistsThenReturnBook() {
        when(bookRepository.findById("1")).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.findById("1");

        assertThat(foundBook).isPresent();
        assertThat(foundBook.get()).isEqualTo(book);
    }

    @Test
    void testFindByIdWhenBookDoesNotExistThenReturnEmpty() {
        when(bookRepository.findById("1")).thenReturn(Optional.empty());

        Optional<Book> foundBook = bookService.findById("1");

        assertThat(foundBook).isNotPresent();
    }

    @Test
    void testFindAllWhenBooksExistThenReturnListOfBooks() {
        List<Book> books = List.of(book);
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> foundBooks = bookService.findAll();

        assertThat(foundBooks).isNotEmpty();
        assertThat(foundBooks).hasSize(1);
        assertThat(foundBooks).contains(book);
    }

    @Test
    void testInsertWhenValidDataThenReturnInsertedBook() {
        when(authorRepository.findById("1")).thenReturn(Optional.of(author));
        when(genreRepository.findById("1")).thenReturn(Optional.of(genre));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book insertedBook = bookService.insert("Book Title", "1", "1");

        assertThat(insertedBook).isEqualTo(book);
    }

    @Test
    void testUpdateWhenValidDataThenReturnUpdatedBook() {
        when(bookRepository.findById("1")).thenReturn(Optional.of(book));
        when(authorRepository.findById("1")).thenReturn(Optional.of(author));
        when(genreRepository.findById("1")).thenReturn(Optional.of(genre));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book updatedBook = bookService.update("1", "Updated Title", "1", "1");

        assertThat(updatedBook).isEqualTo(book);
        assertThat(updatedBook.getTitle()).isEqualTo("Updated Title");
    }

    @Test
    void testUpdateWhenBookDoesNotExistThenThrowEntityNotFoundException() {
        when(bookRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.update("1", "Updated Title", "1", "1"));
    }

    @Test
    void testDeleteByIdWhenBookExistsThenDeleteBookAndComments() {
        doNothing().when(commentRepository).deleteByBookId("1");
        doNothing().when(bookRepository).deleteById("1");

        bookService.deleteById("1");

        verify(commentRepository, times(1)).deleteByBookId("1");
        verify(bookRepository, times(1)).deleteById("1");
    }
}