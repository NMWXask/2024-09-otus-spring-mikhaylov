package spring.otus.repositories;


import spring.otus.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    List<Comment> findAllByBook(Long bookId);

    Optional<Comment> findById(long id);

    Comment save(Comment comment);

    void deleteById(long id);

    void deleteAllByBookId(long id);

}
