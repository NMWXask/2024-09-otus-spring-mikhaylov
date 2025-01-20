package ru.otus.hw05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.otus.hw05.exception.EntityNotFoundException;
import ru.otus.hw05.model.Author;
import ru.otus.hw05.model.Book;
import ru.otus.hw05.model.Genre;
import ru.otus.hw05.repository.JdbcBookRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jdbc для работы с книгами ")
@JdbcTest
@Import({JdbcBookRepository.class})
class JdbcBookRepositoryTest {

    @Autowired
    private JdbcBookRepository jdbcBookRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;
    private List<Author> dbAuthors;

    private List<Genre> dbGenres;

    private List<Book> dbBooks;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        dbBooks =getDbBooks(dbAuthors,dbGenres);
    }

    @DisplayName("должен загружать книгу по id")
    void shouldReturnCorrectBookById() {
        for (Book expectedBook : dbBooks) {
            var actualBook = jdbcBookRepository.findById(expectedBook.getId());
            assertThat(actualBook).isPresent()
                    .get()
                    .isEqualTo(expectedBook);
        }
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        var actualBooks = jdbcBookRepository.findAll();
        var expectedBooks = dbBooks;

        assertThat(actualBooks).containsExactlyElementsOf(expectedBooks);
        actualBooks.forEach(System.out::println);
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var expectedBook = new Book(null, "BookTitle_10500", dbAuthors.get(0), dbGenres.get(0));
        var returnedBook = jdbcBookRepository.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(jdbcBookRepository.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        var expectedBook = new Book(1L, "BookTitle_10500", dbAuthors.get(2), dbGenres.get(2));

        assertThat(jdbcBookRepository.findById(expectedBook.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedBook);

        var returnedBook = jdbcBookRepository.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(jdbcBookRepository.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        assertThat(jdbcBookRepository.findById(1L)).isPresent();
        jdbcBookRepository.deleteById(1L);
        assertThat(jdbcBookRepository.findById(1L)).isEmpty();
    }

    private  List<Author> getDbAuthors() {

        return jdbcTemplate.query(
                "select id, name from authors",
                (resultSet, rowNum) ->
                        new Author(
                                resultSet.getLong("id"),
                                resultSet.getString("name")
                        )
        );

    }

    private  List<Genre> getDbGenres() {
        return jdbcTemplate.query(
                "select id, name from genres",
                (resultSet, rowNum) ->
                        new Genre(
                                resultSet.getLong("id"),
                                resultSet.getString("name")
                        )
                );
    }

    private  List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {

        String sql = "select id, title, AUTHOR_ID, GENRE_ID from books";

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) ->  {
                        long authorId = resultSet.getLong("AUTHOR_ID");
                        long  genreId = resultSet.getLong("GENRE_ID");

                        return new Book(
                                resultSet.getLong("id"),
                                resultSet.getString("title"),
                                dbAuthors.stream().filter(genre -> genre.getId() == authorId).findAny()
                                        .orElseThrow(() ->
                                                new EntityNotFoundException("Author with id %d not found".formatted(authorId))
                                        ),
                                dbGenres.stream().filter(author -> author.getId() == genreId).findAny()
                                        .orElseThrow(() ->
                                                new EntityNotFoundException("Genre with id %d not found".formatted(genreId))
                                        )
                        );
                } );
    }
}