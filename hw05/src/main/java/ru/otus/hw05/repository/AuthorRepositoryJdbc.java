package ru.otus.hw05.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw05.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJdbc implements AuthorRepository {

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;


    @Override
    public List<Author> findAll() {
        return namedParameterJdbcOperations.getJdbcOperations()
                .query("select id, name from authors", new AuthorMapper());
    }

    @Override
    public Optional<Author> findById(long id) {
        try {
            return Optional.ofNullable(namedParameterJdbcOperations.queryForObject(
                    "select id, name from authors where id = :id",
                    Map.of("id", id), new AuthorMapper()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Author save(Author genre) {
        if (genre.getId() == null) {
            return insert(genre);
        }
        return update(genre);
    }

    @Override
    public long deleteById(long id) {
        return namedParameterJdbcOperations.update(
                "delete from authors where id = :id", Map.of("id", id));
    }

    private Author insert(Author author) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", author.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update(
                "insert into authors (name) values (:name)", parameterSource, keyHolder, new String[]{"id"});
        author.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return author;
    }

    private Author update(Author author) {
        namedParameterJdbcOperations.update(
                "update authors g set g.name = :name where g.id = :id",
                Map.of("id", author.getId(), "name", author.getName()));
        return author;
    }
}
