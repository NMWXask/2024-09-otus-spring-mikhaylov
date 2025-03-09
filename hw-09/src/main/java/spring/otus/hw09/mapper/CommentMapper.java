package spring.otus.hw09.mapper;

import org.mapstruct.Mapper;
import spring.otus.hw09.dto.CommentDto;
import spring.otus.hw09.models.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto toDto(Comment comment);
}
