package spring.otus.services;

import spring.otus.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> findAllByBook(Long bookId);

    Optional<Comment> findById(long id);

    void deleteById(long id);

    Comment create(long bookId, String text);

    Comment update(long id, String text);

}
