package ru.otus.hw05.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw05.model.Author;
import ru.otus.hw05.model.Book;
import ru.otus.hw05.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BookRepositoryJdbc implements BookRepository {

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            return new Book(resultSet.getLong("b_id"), resultSet.getString("b_title"),
                    new Author(resultSet.getLong("a_id"), resultSet.getString("name")),
                    new Genre(resultSet.getLong("g_id"), resultSet.getString("g_name"))
            );
        }
    }


    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Optional<Book> findById(long id) {
        String sql = "select b.id as b_id, b.title as b_title, a.id as a_id, a.name, "
                     + "g.id as g_id, g.name as g_name from books b " +
                     "join authors a on b.author_id = a.id " +
                     "join genres g on b.genre_id = g.id where b.id = :id";
        try {
            return Optional.ofNullable(namedParameterJdbcOperations.queryForObject(sql,
                    Map.of("id", id), new BookMapper()));
        } catch (DataAccessException e) {
            log.error("Error while fetching book by id: {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        String sql = "select b.id as b_id, b.title as b_title, a.id as a_id, a.name, "
                     + "g.id as g_id, g.name as g_name from books b " +
                     "join authors a on b.author_id=a.id " +
                     "join genres g on b.genre_id=g.id ";
        return namedParameterJdbcOperations.query(sql, new BookMapper());
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbcOperations.update(
                "delete from books where id = :id", Map.of("id", id));
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("author_id", book.getAuthor().getId());
        parameterSource.addValue("genre_id", book.getGenre().getId());
        namedParameterJdbcOperations.update(
                "insert into books (title, author_id, genre_id) values (:title, :author_id, :genre_id)",
                parameterSource, keyHolder, new String[]{"id"});
        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {
        namedParameterJdbcOperations.update(
                "update books set title = :title, author_id = :author_id, genre_id = :genre_id where id = :id",
                Map.of("id", book.getId(), "title", book.getTitle(),
                        "author_id", book.getAuthor().getId(), "genre_id", book.getGenre().getId()));
        return book;
    }
}