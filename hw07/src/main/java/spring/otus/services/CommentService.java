package spring.otus.services;

import spring.otus.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    public List<Comment> findAllByBook(Long bookId);

    public Optional<Comment> findById(long id);

    public void deleteById(long id);

    public Comment create(long bookId, String text);

    public Comment update(long id, String text);

    public void deleteByBookId(long bookId);

}
