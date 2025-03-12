package spring.otus.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.otus.models.Author;
import spring.otus.models.Book;
import spring.otus.models.Genre;
import spring.otus.repositories.AuthorRepository;
import spring.otus.repositories.BookRepository;
import spring.otus.repositories.GenreRepository;
import spring.otus.services.BookServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void testFindByIdWhenBookExistsThenReturnBook() {
        long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.findById(bookId);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    void testFindByIdWhenBookDoesNotExistThenReturnEmptyOptional() {
        long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Optional<Book> result = bookService.findById(bookId);

        assertThat(result).isEmpty();
    }

    @Test
    void testFindFullByIdWhenBookExistsThenReturnBook() {
        long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        when(bookRepository.findWithCommentsById(bookId)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.findFullById(bookId);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    void testFindAllWhenBooksExistThenReturnBooks() {
        List<Book> books = List.of(new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.findAll();

        assertThat(result).isEqualTo(books);
    }

    @Test
    void testInsertWhenValidDataThenSaveBook() {
        String title = "New Book";
        long authorId = 1L;
        long genreId = 1L;
        Author author = new Author();
        author.setId(authorId);
        Genre genre = new Genre();
        genre.setId(genreId);
        Book book = new Book(0L, title, author, genre, null);

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.insert(title, authorId, genreId);

        assertThat(result).isEqualTo(book);
    }

    @Test
    void testUpdateWhenValidDataThenUpdateBook() {
        long bookId = 1L;
        String title = "Updated Book";
        long authorId = 1L;
        long genreId = 1L;
        Author author = new Author();
        author.setId(authorId);
        Genre genre = new Genre();
        genre.setId(genreId);
        Book book = new Book(bookId, title, author, genre, null);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.update(bookId, title, authorId, genreId);

        assertThat(result).isEqualTo(book);
    }

    @Test
    void testDeleteByIdWhenBookExistsThenDeleteBook() {
        long bookId = 1L;

        bookService.deleteById(bookId);

        verify(bookRepository, times(1)).deleteById(bookId);
    }
}
