package spring.otus.hw08.services;

import spring.otus.hw08.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    public List<Comment> findAllByBook(String bookId);

    public Optional<Comment> findById(String id);

    public void deleteById(String id);

    public Comment create(String bookId, String text);

    public Comment update(String id, String text);

    public void deleteByBookId(String bookId);
}
