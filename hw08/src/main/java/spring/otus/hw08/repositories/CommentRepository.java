package spring.otus.hw08.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import spring.otus.hw08.models.Comment;

import java.util.List;

public interface CommentRepository  extends MongoRepository<Comment, String> {

    public List<Comment> findAllByBookId(String bookId);
 
    public void deleteByBookId(String id);
}
