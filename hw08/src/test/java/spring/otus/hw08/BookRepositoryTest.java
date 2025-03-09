package spring.otus.hw08;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import spring.otus.hw08.models.Author;
import spring.otus.hw08.models.Book;
import spring.otus.hw08.models.Genre;
import spring.otus.hw08.repositories.AuthorRepository;
import spring.otus.hw08.repositories.BookRepository;
import spring.otus.hw08.repositories.GenreRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с книгами ")
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"spring.otus.hw08.repositories"})
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    AuthorRepository authorRepository;



    private List<Author> dbAuthors;

    private List<Genre> dbGenres;

    private List<Book> dbBooks;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        dbBooks =getDbBooks(dbAuthors,dbGenres);

        if (dbAuthors.isEmpty()) {
            Author author = new Author("1", "AuthorName");
            authorRepository.save(author);
            dbAuthors = getDbAuthors();
        }

        if (dbGenres.isEmpty()) {
            Genre genre = new Genre("1", "GenreName");
            genreRepository.save(genre);
            dbGenres = getDbGenres();
        }
    }

    @DisplayName("должен загружать книгу по id")
    void shouldReturnCorrectBookById() {
        for (int i = 0; i < dbBooks.size(); i++) {
            var expectedBook = dbBooks.get(i);
            var actualBook = bookRepository.findById(expectedBook.getId());
            assertThat(actualBook).isPresent()
                    .get()
                    .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);
        }
    }


    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var expectedBook = new Book("1", "BookTitle_10500", dbAuthors.get(0), dbGenres.get(0));
        var returnedBook = bookRepository.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId().equals("1"))
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        var expectedBook = new Book("1", "BookTitle_10500", dbAuthors.get(0), dbGenres.get(0));
        bookRepository.save(expectedBook);

        assertThat(bookRepository.findById(expectedBook.getId()))
                .isPresent()
                .get()
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        var returnedBook = bookRepository.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId().equals("1"))
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(bookRepository.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(returnedBook);
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        for (Book expectedBook : dbBooks) {
            assertThat(bookRepository.findById(expectedBook.getId())).isPresent();
            bookRepository.deleteById(expectedBook.getId());
            assertThat(bookRepository.findById(expectedBook.getId())).isEmpty();
        }
    }

    private  List<Author> getDbAuthors() {
        return authorRepository.findAll();
    }

    private  List<Genre> getDbGenres() {
        return genreRepository.findAll();
    }

    private  List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return bookRepository.findAll();
    }
}