package spring.otus.hw10.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.otus.hw10.dto.CommentCreateDto;
import spring.otus.hw10.dto.CommentDto;
import spring.otus.hw10.models.Book;
import spring.otus.hw10.models.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto toDto(Comment comment);

    @Mapping(target = "book", source = "book")
    @Mapping(target = "id", ignore = true)
    Comment toModel(CommentCreateDto comment, Book book);

}
