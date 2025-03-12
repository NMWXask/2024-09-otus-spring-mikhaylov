package spring.otus.hw11.mapper;

import org.mapstruct.Mapper;
import spring.otus.hw11.dto.CommentCreateDto;
import spring.otus.hw11.dto.CommentDto;
import spring.otus.hw11.dto.CommentUpdateDto;
import spring.otus.hw11.models.Book;
import spring.otus.hw11.models.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto toDto(Comment comment);

    default Comment toModel(CommentUpdateDto comment) {
        return new Comment (comment.id(),
                comment.text(),
                new Book());
    }

    default Comment toModel(CommentCreateDto comment) {
        return new Comment (null,
                comment.text(),
                new Book(comment.bookId(), null, null, null));
    }
}
