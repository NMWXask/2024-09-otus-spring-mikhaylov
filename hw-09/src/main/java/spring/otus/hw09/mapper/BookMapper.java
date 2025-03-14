package spring.otus.hw09.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import spring.otus.hw09.dto.BookDto;
import spring.otus.hw09.dto.BookUpdateDto;
import spring.otus.hw09.models.Book;

@Mapper(componentModel = "spring", uses = {CommentMapper.class})
public interface BookMapper {

    @Mappings({
            @Mapping(target = "authorId", source = "book.author.id"),
            @Mapping(target = "genreId", source = "book.genre.id")
    })
    BookUpdateDto toEditDto(Book book);

    @Mappings({
            @Mapping(target = "author", source = "book.author.name"),
            @Mapping(target = "genre", source = "book.genre.name")
    })
    BookDto toDto(Book book);

}
