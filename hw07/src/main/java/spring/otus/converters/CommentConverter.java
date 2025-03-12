package spring.otus.converters;

import org.springframework.stereotype.Component;
import spring.otus.models.Comment;

@Component
public class CommentConverter {
    public String commentToString(Comment comment) {
        return "Id: %d, Comment: %s".formatted(comment.getId(), comment.getText());
    }
}
