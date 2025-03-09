package spring.otus.hw10.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.otus.hw10.models.Comment;

import java.util.List;

public interface CommentRepository  extends JpaRepository<Comment, Long> {

    public List<Comment> findAllByBookId(Long bookId);
 
    public void deleteByBookId(long id);

}
