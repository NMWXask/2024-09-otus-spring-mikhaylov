package spring.otus.hw08.converters;

import org.springframework.stereotype.Component;
import spring.otus.hw08.models.Comment;

@Component
public class CommentConverter {
    public String commentToString(Comment comment) {
        return "Id: %s, Comment: %s".formatted(comment.getId(), comment.getText());
    }
}
